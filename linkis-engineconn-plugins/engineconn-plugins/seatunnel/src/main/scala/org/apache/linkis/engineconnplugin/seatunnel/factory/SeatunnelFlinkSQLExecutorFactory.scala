package org.apache.linkis.engineconnplugin.seatunnel.factory

import org.apache.linkis.engineconn.common.creation.EngineCreationContext
import org.apache.linkis.engineconn.common.engineconn.EngineConn
import org.apache.linkis.engineconn.once.executor.OnceExecutor
import org.apache.linkis.engineconn.once.executor.creation.OnceExecutorFactory
import org.apache.linkis.manager.label.entity.Label
import org.apache.linkis.manager.label.entity.engine.RunType
import org.apache.linkis.manager.label.entity.engine.RunType.RunType

class SeatunnelFlinkSQLExecutorFactory extends OnceExecutorFactory{

  override protected def getRunType: RunType = RunType.SEATUNNEL_FLINK_SQL

  override protected def newExecutor(id: Int, engineCreationContext: EngineCreationContext, engineConn: EngineConn, labels: Array[Label[_]]): OnceExecutor = {
    null
  }
}
