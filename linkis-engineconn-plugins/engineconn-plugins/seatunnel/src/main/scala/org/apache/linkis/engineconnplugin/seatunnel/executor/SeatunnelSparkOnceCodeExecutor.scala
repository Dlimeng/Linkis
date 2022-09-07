package org.apache.linkis.engineconnplugin.seatunnel.executor


import org.apache.commons.lang.StringUtils
import org.apache.linkis.common.utils.Utils
import org.apache.linkis.engineconn.common.conf.EngineConnConf.ENGINE_CONN_LOCAL_PATH_PWD_KEY
import org.apache.linkis.engineconn.core.EngineConnObject
import org.apache.linkis.engineconn.once.executor.{OnceExecutorExecutionContext, OperableOnceExecutor}
import org.apache.linkis.engineconnplugin.seatunnel.client.LinkisSeatunnelSparkClient
import org.apache.linkis.engineconnplugin.seatunnel.client.exception.JobExecutionException
import org.apache.linkis.engineconnplugin.seatunnel.config.{SeatunnelEnvConfiguration, SeatunnelSparkEnvConfiguration}
import org.apache.linkis.engineconnplugin.seatunnel.context.SeatunnelEngineConnContext
import org.apache.linkis.manager.common.entity.resource.{CommonNodeResource, LoadInstanceResource, NodeResource}
import org.apache.linkis.manager.engineplugin.common.conf.EngineConnPluginConf
import org.apache.linkis.protocol.constants.TaskConstant
import org.apache.linkis.protocol.engine.JobProgressInfo
import org.apache.linkis.scheduler.executer.ErrorExecuteResponse

import java.io.{File, PrintWriter}
import java.nio.file.Files
import java.util
import java.util.concurrent.{Future, TimeUnit}

class SeatunnelSparkOnceCodeExecutor(override val id: Long,override protected val seatunnelEngineConnContext: SeatunnelEngineConnContext) extends SeatunnelSparkOnceExecutor with OperableOnceExecutor {
  private var params: util.Map[String, String] = _
  private var future: Future[_] = _
  private var daemonThread: Future[_] = _
  var isFailed = false

  override def doSubmit(onceExecutorExecutionContext: OnceExecutorExecutionContext, options: Map[String, String]): Unit = {
    val code: String = options(TaskConstant.CODE)
    params = onceExecutorExecutionContext.getOnceExecutorContent.getJobContent.asInstanceOf[util.Map[String, String]]
    future = Utils.defaultScheduler.submit(new Runnable {
      override def run(): Unit = {
        info("Try to execute codes."+code)
        if(runCode(code) !=0){
          isFailed = true
          setResponse(ErrorExecuteResponse("Run code failed!", new JobExecutionException("Exec Seatunnel Spark Code Error")))
          tryFailed()
        }
        info("All codes completed, now stop SeatunnelSparkEngineConn.")
        closeDaemon()
        if(!isFailed) {
          trySucceed()
        }
        this synchronized notify()
      }
    })
  }

  protected def runCode(code: String):Int = {
    info("Execute SeatunnelSpark Process")
    val masterKey = SeatunnelSparkEnvConfiguration.LINKIS_SPARK_MASTER.getValue
    val configKey = SeatunnelSparkEnvConfiguration.LINKIS_SPARK_CONFIG.getValue
    val deployModeKey = SeatunnelSparkEnvConfiguration.LINKIS_SPARK_DEPLOY_MODE.getValue

    var args = localArray(generateExecFile(code))
    if(params != null && StringUtils.isNotBlank(params.get(masterKey))) {
      args = Array(masterKey,params.getOrDefault(masterKey,"yarn"),
        deployModeKey,params.getOrDefault(deployModeKey,"client"),
        configKey,generateExecFile(code))
    }
    System.setProperty("SEATUNNEL_HOME",System.getenv(ENGINE_CONN_LOCAL_PATH_PWD_KEY.getValue));
    Files.createSymbolicLink(new File(System.getenv(ENGINE_CONN_LOCAL_PATH_PWD_KEY.getValue)+"/seatunnel").toPath,new File(SeatunnelEnvConfiguration.SEATUNNEL_HOME.getValue).toPath)
    LinkisSeatunnelSparkClient.main(args)
  }
  private def localArray(code: String): Array[String] ={
     Array(SeatunnelSparkEnvConfiguration.LINKIS_SPARK_CONFIG.getValue,generateExecFile(code))
  }


  private def generateExecFile(code: String) :String = {
    val file = new File(System.getenv(ENGINE_CONN_LOCAL_PATH_PWD_KEY.getValue)+"/job_"+System.currentTimeMillis())
    val writer = new PrintWriter(file)
    writer.write(code)
    writer.close()
    file.getAbsolutePath
  }

  override protected def waitToRunning(): Unit = {
    if (!isCompleted) daemonThread = Utils.defaultScheduler.scheduleAtFixedRate(new Runnable {
      override def run(): Unit = {
        if (!(future.isDone || future.isCancelled)) {
          info("The Seatunnel Spark Process In Running")
        }
      }
    }, SeatunnelEnvConfiguration.SEATUNNEL_STATUS_FETCH_INTERVAL.getValue.toLong,
      SeatunnelEnvConfiguration.SEATUNNEL_STATUS_FETCH_INTERVAL.getValue.toLong, TimeUnit.MILLISECONDS)
  }
  override def getCurrentNodeResource(): NodeResource = {
    val properties = EngineConnObject.getEngineCreationContext.getOptions
    if (properties.containsKey(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.key)) {
      val settingClientMemory = properties.get(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.key)
      if (!settingClientMemory.toLowerCase().endsWith("g")) {
        properties.put(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.key, settingClientMemory + "g")
      }
    }
    val actualUsedResource = new LoadInstanceResource(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.getValue(properties).toLong,
      EngineConnPluginConf.JAVA_ENGINE_REQUEST_CORES.getValue(properties), EngineConnPluginConf.JAVA_ENGINE_REQUEST_INSTANCE)
    val resource = new CommonNodeResource
    resource.setUsedResource(actualUsedResource)
    resource
  }

  protected def closeDaemon(): Unit = {
    if (daemonThread != null) daemonThread.cancel(true)
  }

  override def getProgress: Float = 0f

  override def getProgressInfo: Array[JobProgressInfo] = {
    Array.empty[JobProgressInfo]
  }


  override def getMetrics: util.Map[String, Any] = {
    new util.HashMap[String,Any]()
  }

  override def getDiagnosis: util.Map[String, Any] = new util.HashMap[String,Any]()
}
