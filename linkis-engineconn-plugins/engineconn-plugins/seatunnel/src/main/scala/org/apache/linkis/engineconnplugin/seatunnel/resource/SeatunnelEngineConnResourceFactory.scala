package org.apache.linkis.engineconnplugin.seatunnel.resource

import org.apache.linkis.manager.common.entity.resource.{LoadInstanceResource, Resource}
import org.apache.linkis.manager.engineplugin.common.resource.AbstractEngineResourceFactory

import java.util

class SeatunnelEngineConnResourceFactory extends AbstractEngineResourceFactory{
  override protected def getRequestResource(properties: util.Map[String, String]): Resource = new LoadInstanceResource(1,
    1,
    1)
}
