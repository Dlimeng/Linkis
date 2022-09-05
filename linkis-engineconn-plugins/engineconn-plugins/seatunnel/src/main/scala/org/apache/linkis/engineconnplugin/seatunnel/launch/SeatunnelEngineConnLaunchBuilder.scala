package org.apache.linkis.engineconnplugin.seatunnel.launch

import org.apache.commons.lang3.StringUtils
import org.apache.linkis.engineconnplugin.seatunnel.config.SeatunnelEnvConfiguration
import org.apache.linkis.manager.engineplugin.common.launch.entity.EngineConnBuildRequest
import org.apache.linkis.manager.engineplugin.common.launch.process.Environment.{HADOOP_CONF_DIR, variable}
import org.apache.linkis.manager.engineplugin.common.launch.process.JavaProcessEngineConnLaunchBuilder
import org.apache.linkis.manager.engineplugin.common.launch.process.LaunchConstants.addPathToClassPath

import scala.collection.JavaConverters._
import java.nio.file.Paths
import java.util

class SeatunnelEngineConnLaunchBuilder  extends JavaProcessEngineConnLaunchBuilder{

  override protected def getEnvironment(implicit engineConnBuildRequest: EngineConnBuildRequest): util.Map[String, String] = {
    val environment = super.getEnvironment
    // Basic classpath
    addPathToClassPath(environment, variable(HADOOP_CONF_DIR))
    if (StringUtils.isNotBlank(SeatunnelEnvConfiguration.SEATUNNEL_HOME.getValue)) {
      addPathToClassPath(environment, Seq(SeatunnelEnvConfiguration.SEATUNNEL_HOME.getValue, "/*"))
    }

    if (StringUtils.isNotBlank(SeatunnelEnvConfiguration.SPARK_HOME.getValue)) {
      addPathToClassPath(environment, Seq(SeatunnelEnvConfiguration.SPARK_HOME.getValue, "/*"))
    }

    if (StringUtils.isNotBlank(SeatunnelEnvConfiguration.FLINK_HOME.getValue)) {
      addPathToClassPath(environment, Seq(SeatunnelEnvConfiguration.FLINK_HOME.getValue, "/*"))
    }

    environment
  }


  override protected def getNecessaryEnvironment(implicit engineConnBuildRequest: EngineConnBuildRequest): Array[String] = {
    // To submit a mapReduce job, we should load the configuration from hadoop config dir
    Array(HADOOP_CONF_DIR.toString, SeatunnelEnvConfiguration.SEATUNNEL_HOME.key)
  }

  private implicit def buildPath(paths: Seq[String]): String = Paths.get(paths.head, paths.tail: _*).toFile.getPath

}
