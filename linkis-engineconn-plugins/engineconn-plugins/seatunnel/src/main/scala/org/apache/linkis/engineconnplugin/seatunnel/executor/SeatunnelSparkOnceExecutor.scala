package org.apache.linkis.engineconnplugin.seatunnel.executor

import org.apache.linkis.common.utils.Utils
import org.apache.linkis.engineconn.once.executor.{ManageableOnceExecutor, OnceExecutorExecutionContext}
import org.apache.linkis.manager.common.entity.enumeration.NodeStatus
import scala.collection.convert.WrapAsScala._

trait SeatunnelSparkOnceExecutor  extends ManageableOnceExecutor with SeatunnelSparkExecutor{
  protected def submit(onceExecutorExecutionContext: OnceExecutorExecutionContext): Unit = {
    val options = onceExecutorExecutionContext.getOnceExecutorContent.getJobContent.map {
      case (k, v: String) => k -> v
      case (k, v) if v != null => k -> v.toString
      case (k, _) => k -> null
    }.toMap
    doSubmit(onceExecutorExecutionContext, options)
  }
  def doSubmit(onceExecutorExecutionContext: OnceExecutorExecutionContext, options: Map[String, String]): Unit

  val id: Long

  override def getId: String = "SeatunnelSparkOnceApp_" + id
  override def close(): Unit = {
    //Sqoop.close()
    super.close()
  }
  override def trySucceed(): Boolean = {
    super.trySucceed()
  }


  override def ensureAvailable[A](f: => A): A = {
    // Not need to throws exception
    Utils.tryQuietly{ super.ensureAvailable(f) }
  }

  override def tryFailed(): Boolean = {
    //LinkisSqoopClient.close()
    super.tryFailed()
  }

  override def supportCallBackLogs(): Boolean = true


  protected def isCompleted: Boolean = isClosed || NodeStatus.isCompleted(getStatus)
}
