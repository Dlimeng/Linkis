package org.apache.linkis.engineconnplugin.seatunnel.util

import org.apache.commons.io.IOUtils
import org.apache.linkis.engineconn.acessible.executor.log.LogHelper
import org.apache.linkis.engineconn.common.conf.EngineConnConf.ENGINE_CONN_LOCAL_PATH_PWD_KEY
import org.apache.linkis.engineconnplugin.seatunnel.config.SeatunnelSparkEnvConfiguration
import org.slf4j.LoggerFactory

import java.io.{BufferedReader, File, InputStreamReader, PrintWriter}

object SeatunnelUtils {
  val logger = LoggerFactory.getLogger(getClass)
  private var process: Process = _

   def localArray(code: String): Array[String] ={
    Array(SeatunnelSparkEnvConfiguration.LINKIS_SPARK_CONFIG.getValue,generateExecFile(code))
  }

   def generateExecFile(code: String) :String = {
    val file = new File(System.getenv(ENGINE_CONN_LOCAL_PATH_PWD_KEY.getValue)+"/config_"+System.currentTimeMillis())
    val writer = new PrintWriter(file)
    writer.write(code)
    writer.close()
    file.getAbsolutePath
  }

  def executeLine(code: String): Int ={
    var bufferedReader: BufferedReader = null
    var errorsReader: BufferedReader = null
    try{
      val processBuilder: ProcessBuilder = new ProcessBuilder(generateRunCode(code): _*)
      process = processBuilder.start()
      bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream))
      errorsReader = new BufferedReader(new InputStreamReader(process.getErrorStream))
      var line: String = null
      while ( {
        line = bufferedReader.readLine(); line != null
      }) {
        logger.info("executeLine:"+line)
        LogHelper.logCache.cacheLog(line)
      }
      val exitcode = process.waitFor()
      logger.info("executeLine exitcode:"+exitcode)
      exitcode
    }finally {
      IOUtils.closeQuietly(bufferedReader)
      IOUtils.closeQuietly(errorsReader)
    }
  }

  private def generateRunCode(code: String): Array[String] = {
    Array("sh", "-c", code)
  }
}
