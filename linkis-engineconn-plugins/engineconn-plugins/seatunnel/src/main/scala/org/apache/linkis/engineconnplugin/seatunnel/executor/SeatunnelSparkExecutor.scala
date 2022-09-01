package org.apache.linkis.engineconnplugin.seatunnel.executor

import org.apache.linkis.engineconn.executor.entity.{LabelExecutor, ResourceExecutor, YarnExecutor}
import org.apache.linkis.engineconnplugin.seatunnel.client.exception.JobExecutionException
import org.apache.linkis.engineconnplugin.seatunnel.context.{SeatunnelEngineConnContext, SeatunnelEnvConfiguration}
import org.apache.linkis.manager.common.entity.resource.NodeResource
import org.apache.linkis.manager.label.entity.Label

import java.util

trait SeatunnelSparkExecutor extends YarnExecutor with LabelExecutor with ResourceExecutor{
  private var yarnMode: String = "Client"
  private var executorLabels: util.List[Label[_]] = new util.ArrayList[Label[_]]
  override def getApplicationId: String = ""

  override def getApplicationURL: String = ""

  override def getYarnMode: String = yarnMode
  def setYarnMode(yarnMode: String): Unit = this.yarnMode = yarnMode

  override def getQueue: String = SeatunnelEnvConfiguration.LINKIS_QUEUE_NAME.getValue

  override def getExecutorLabels(): util.List[Label[_]] = executorLabels

  override def setExecutorLabels(labels: util.List[Label[_]]): Unit = this.executorLabels = labels

  override def requestExpectedResource(expectedResource: NodeResource): NodeResource = throw new JobExecutionException("Not support method for requestExpectedResource.")

  protected val seatunnelEngineConnContext: SeatunnelEngineConnContext
}
