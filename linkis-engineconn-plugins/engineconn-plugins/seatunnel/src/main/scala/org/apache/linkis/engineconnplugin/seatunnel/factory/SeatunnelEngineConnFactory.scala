package org.apache.linkis.engineconnplugin.seatunnel.factory

import org.apache.linkis.common.utils.Logging
import org.apache.linkis.engineconn.common.creation.EngineCreationContext
import org.apache.linkis.engineconnplugin.seatunnel.context.SeatunnelEngineConnContext
import org.apache.linkis.manager.engineplugin.common.creation.{ExecutorFactory, MultiExecutorEngineConnFactory}
import org.apache.linkis.manager.label.entity.engine.EngineType
import org.apache.linkis.manager.label.entity.engine.EngineType.EngineType

class SeatunnelEngineConnFactory extends MultiExecutorEngineConnFactory with Logging{

  override def getExecutorFactories: Array[ExecutorFactory] = executorFactoryArray

  override protected def getDefaultExecutorFactoryClass: Class[_ <: ExecutorFactory] =
    classOf[SeatunnelSparkExecutorFactory]

  override protected def getEngineConnType: EngineType = EngineType.SEATUNNEL

  private val executorFactoryArray =   Array[ExecutorFactory](new SeatunnelSparkExecutorFactory, new SeatunnelFlinkSQLExecutorFactory, new SeatunnelFlinkExecutorFactory)

  override protected def createEngineConnSession(engineCreationContext: EngineCreationContext): Any = {
    val seatunnelEngineConnContext = new SeatunnelEngineConnContext()
    seatunnelEngineConnContext
  }
}
