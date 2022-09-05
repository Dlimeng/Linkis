package org.apache.linkis.engineconnplugin.seatunnel.config

import org.apache.linkis.common.conf.{CommonVars, TimeType}

object SeatunnelEnvConfiguration {
  val SEATUNNEL_HOME: CommonVars[String] = CommonVars("SEATUNNEL_HOME", "/opt/install/seatunnel/plugin")
  val SPARK_HOME: CommonVars[String] = CommonVars("SPARK_HOME", "")
  val FLINK_HOME: CommonVars[String] = CommonVars("FLINK_HOME", "")
  val LINKIS_QUEUE_NAME: CommonVars[String] = CommonVars[String]("wds.linkis.rm.yarnqueue", "default")
  val SEATUNNEL_STATUS_FETCH_INTERVAL: CommonVars[TimeType] = CommonVars("seatunnel.fetch.status.interval", new TimeType("5s"))
  val LINKIS_SPARK_TASK_MAP_MEMORY: CommonVars[Int] = CommonVars[Int]("spark.task.map.memory", 2)
  val LINKIS_SPARK_TASK_MAP_CPU_CORES: CommonVars[Int] = CommonVars[Int]("spark.task.map.cpu.cores", 1)
}
