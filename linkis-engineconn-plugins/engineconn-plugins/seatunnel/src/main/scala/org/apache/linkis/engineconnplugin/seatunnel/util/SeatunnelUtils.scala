package org.apache.linkis.engineconnplugin.seatunnel.util

import org.apache.linkis.engineconn.common.conf.EngineConnConf.ENGINE_CONN_LOCAL_PATH_PWD_KEY
import org.apache.linkis.engineconnplugin.seatunnel.config.SeatunnelSparkEnvConfiguration

import java.io.{File, PrintWriter}

object SeatunnelUtils {
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
}
