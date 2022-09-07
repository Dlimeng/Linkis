package org.apache.linkis.engineconnplugin.seatunnel.config

import org.apache.linkis.common.conf.{CommonVars, TimeType}

object SeatunnelEnvConfiguration {
  val SEATUNNEL_HOME: CommonVars[String] = CommonVars("SEATUNNEL_HOME", "/opt/install/seatunnel/plugin")
  val SEATUNNEL_STATUS_FETCH_INTERVAL: CommonVars[TimeType] = CommonVars("seatunnel.fetch.status.interval", new TimeType("5s"))

}
