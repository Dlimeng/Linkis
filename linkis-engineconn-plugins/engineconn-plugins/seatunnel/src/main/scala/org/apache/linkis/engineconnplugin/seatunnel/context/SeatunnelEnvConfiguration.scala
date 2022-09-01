/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.linkis.engineconnplugin.seatunnel.context

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
