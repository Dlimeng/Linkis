package org.apache.linkis.engineconnplugin.seatunnel.config

import org.apache.linkis.common.conf.CommonVars

object SeatunnelSparkEnvConfiguration {
  val LINKIS_SPARK_MASTER: CommonVars[String] = CommonVars[String]("linkis.spark.master", "master")
  val LINKIS_SPARK_DEPLOY_MODE: CommonVars[String] = CommonVars[String]("linkis.spark.deploy.mode", "deploy-mode")
  val LINKIS_SPARK_CONFIG: CommonVars[String] = CommonVars[String]("linkis.spark.deploy.mode", "config")
  val LINKIS_SPARK_TASK_MAP_MEMORY: CommonVars[Int] = CommonVars[Int]("spark.task.map.memory", 2)
  val LINKIS_SPARK_TASK_MAP_CPU_CORES: CommonVars[Int] = CommonVars[Int]("spark.task.map.cpu.cores", 1)

}
