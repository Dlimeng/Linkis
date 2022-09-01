package org.apache.linkis.engineconnplugin.seatunnel.factory

import org.apache.linkis.engineconn.common.creation.EngineCreationContext
import org.apache.linkis.engineconn.common.engineconn.EngineConn
import org.apache.linkis.engineconn.once.executor.OnceExecutor
import org.apache.linkis.engineconn.once.executor.creation.OnceExecutorFactory
import org.apache.linkis.engineconnplugin.seatunnel.context.SeatunnelEngineConnContext
import org.apache.linkis.engineconnplugin.seatunnel.executor.SeatunnelSparkOnceCodeExecutor
import org.apache.linkis.manager.label.entity.Label
import org.apache.linkis.manager.label.entity.engine.RunType
import org.apache.linkis.manager.label.entity.engine.RunType.RunType

class SeatunnelSparkExecutorFactory extends OnceExecutorFactory{
  override protected def newExecutor(id: Int, engineCreationContext: EngineCreationContext, engineConn: EngineConn, labels: Array[Label[_]]): OnceExecutor = {
    engineConn.getEngineConnSession match {
      case context: SeatunnelEngineConnContext =>
        new SeatunnelSparkOnceCodeExecutor(id, context)
    }
  }

  override protected def getRunType: RunType = RunType.SEATUNNEL_SPARK
}
