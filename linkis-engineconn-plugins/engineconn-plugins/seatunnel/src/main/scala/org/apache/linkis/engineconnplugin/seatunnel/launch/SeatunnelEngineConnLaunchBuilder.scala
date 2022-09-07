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

}
