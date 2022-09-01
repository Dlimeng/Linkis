package org.apache.linkis.engineconnplugin.seatunnel.executor

import org.apache.linkis.common.utils.Utils
import org.apache.linkis.engineconn.common.creation.EngineCreationContext
import org.apache.linkis.engineconn.core.EngineConnObject
import org.apache.linkis.engineconn.once.executor.{OnceExecutorExecutionContext, OperableOnceExecutor}
import org.apache.linkis.engineconnplugin.seatunnel.context.{SeatunnelEngineConnContext, SeatunnelEnvConfiguration}
import org.apache.linkis.manager.common.entity.resource.{CommonNodeResource, DriverAndYarnResource, LoadInstanceResource, NodeResource, YarnResource}
import org.apache.linkis.manager.engineplugin.common.conf.EngineConnPluginConf
import org.apache.linkis.protocol.engine.JobProgressInfo

import java.util
import java.util.concurrent.{Future, TimeUnit}

class SeatunnelSparkOnceCodeExecutor(override val id: Long,override protected val seatunnelEngineConnContext: SeatunnelEngineConnContext) extends SeatunnelSparkOnceExecutor with OperableOnceExecutor {
  private var params: util.Map[String, String] = _
  private var future: Future[_] = _
  private var daemonThread: Future[_] = _
  //private val paramsResolvers: Array[SqoopParamsResolver] = Array()

  override def doSubmit(onceExecutorExecutionContext: OnceExecutorExecutionContext, options: Map[String, String]): Unit = {
    var isFailed = false
    future = Utils.defaultScheduler.submit(new Runnable {
      override def run(): Unit = {
        // TODO filter job content
//        params = onceExecutorExecutionContext.getOnceExecutorContent.getJobContent.asInstanceOf[util.Map[String, String]]
//        info("Try to execute params." + params)
//        if(runSqoop(params, onceExecutorExecutionContext.getEngineCreationContext) != 0) {
//          isFailed = true
//          tryFailed()
//          setResponse(ErrorExecuteResponse("Run code failed!", new JobExecutionException("Exec Sqoop Code Error")))
//        }
//        info("All codes completed, now to stop SqoopEngineConn.")
//        closeDaemon()
//        if (!isFailed) {
//          trySucceed()
//        }
//        this synchronized notify()
      }
    })
  }
  protected def runSqoop(params: util.Map[String, String], context: EngineCreationContext): Int = {
    Utils.tryCatch {
//      val finalParams = paramsResolvers.foldLeft(params) {
//        case (newParam, resolver) => resolver.resolve(newParam, context)
//      }
      //LinkisSqoopClient.run(finalParams)
    }{
      case e: Exception =>
        error(s"Run Error Message: ${e.getMessage}", e)
        -1
    }

    0
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
    val memorySuffix = "g"
    val properties = EngineConnObject.getEngineCreationContext.getOptions
    Option(properties.get(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.key)).foreach(memory => {
      if (! memory.toLowerCase.endsWith(memorySuffix)) {
        properties.put(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.key, memory + memorySuffix)
      }
    })
    val resource = new DriverAndYarnResource(
      new LoadInstanceResource(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.getValue(properties).toLong,
        EngineConnPluginConf.JAVA_ENGINE_REQUEST_CORES.getValue(properties),
        EngineConnPluginConf.JAVA_ENGINE_REQUEST_INSTANCE),
      new YarnResource(SeatunnelEnvConfiguration.LINKIS_SPARK_TASK_MAP_MEMORY.getValue * getNumTasks, SeatunnelEnvConfiguration.LINKIS_SPARK_TASK_MAP_CPU_CORES.getValue * getNumTasks, 0, SeatunnelEnvConfiguration.LINKIS_QUEUE_NAME.getValue)
    )
    val engineResource = new CommonNodeResource
    engineResource.setUsedResource(resource)
    engineResource
  }

  def getNumTasks: Int = {
    0
  }
  protected def closeDaemon(): Unit = {
    if (daemonThread != null) daemonThread.cancel(true)
  }

  override def getProgress: Float = null

  override def getProgressInfo: Array[JobProgressInfo] = {
    null
  }


  override def getMetrics: util.Map[String, Any] = {
    null
  }

  override def getDiagnosis: util.Map[String, Any] = null
}
