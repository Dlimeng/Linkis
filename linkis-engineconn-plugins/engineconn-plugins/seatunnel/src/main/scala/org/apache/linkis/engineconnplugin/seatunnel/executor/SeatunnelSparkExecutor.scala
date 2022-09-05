package org.apache.linkis.engineconnplugin.seatunnel.executor

import org.apache.linkis.engineconn.executor.entity.{LabelExecutor, ResourceExecutor, YarnExecutor}
import org.apache.linkis.engineconnplugin.seatunnel.client.exception.JobExecutionException
import org.apache.linkis.engineconnplugin.seatunnel.context.SeatunnelEngineConnContext
import org.apache.linkis.manager.common.entity.resource.NodeResource
import org.apache.linkis.manager.label.entity.Label

import java.util

trait SeatunnelSparkExecutor extends LabelExecutor with ResourceExecutor{
  private var executorLabels: util.List[Label[_]] = new util.ArrayList[Label[_]]

  override def getExecutorLabels(): util.List[Label[_]] = executorLabels

  override def setExecutorLabels(labels: util.List[Label[_]]): Unit = this.executorLabels = labels

  override def requestExpectedResource(expectedResource: NodeResource): NodeResource = throw new JobExecutionException("Not support method for requestExpectedResource.")

  protected val seatunnelEngineConnContext: SeatunnelEngineConnContext
}
