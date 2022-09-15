package org.apache.linkis.engineconnplugin.seatunnel.resource

import org.apache.linkis.manager.common.entity.resource.{LoadInstanceResource, Resource}
import org.apache.linkis.manager.engineplugin.common.conf.EngineConnPluginConf
import org.apache.linkis.manager.engineplugin.common.resource.AbstractEngineResourceFactory

import java.util

class SeatunnelEngineConnResourceFactory extends AbstractEngineResourceFactory{
  override protected def getRequestResource(properties: util.Map[String, String]): Resource = {
    val actualUsedResource = new LoadInstanceResource(EngineConnPluginConf.JAVA_ENGINE_REQUEST_MEMORY.getValue(properties).toLong,
      EngineConnPluginConf.JAVA_ENGINE_REQUEST_CORES.getValue(properties), EngineConnPluginConf.JAVA_ENGINE_REQUEST_INSTANCE)
    actualUsedResource
  }
}
