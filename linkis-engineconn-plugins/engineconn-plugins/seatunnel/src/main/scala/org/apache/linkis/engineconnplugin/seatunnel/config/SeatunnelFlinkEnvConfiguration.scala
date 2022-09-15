package org.apache.linkis.engineconnplugin.seatunnel.config

import org.apache.linkis.common.conf.CommonVars

object SeatunnelFlinkEnvConfiguration {
  val LINKIS_FLINK_RUNMODE: CommonVars[String] = CommonVars[String]("linkis.flink.run.mode", "--run-mode")
  val LINKIS_FLINK_CONFIG: CommonVars[String] = CommonVars[String]("linkis.flink.config", "--config")
  val LINKIS_FLINK_VARIABLE: CommonVars[String] = CommonVars[String]("linkis.flink.variable", "--variable")
  val LINKIS_FLINK_CHECK: CommonVars[String] = CommonVars[String]("linkis.flink.check", "--check")
}
