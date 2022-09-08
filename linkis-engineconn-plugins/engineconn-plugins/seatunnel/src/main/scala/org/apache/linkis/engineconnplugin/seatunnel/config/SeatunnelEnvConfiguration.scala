package org.apache.linkis.engineconnplugin.seatunnel.config

import org.apache.linkis.common.conf.{CommonVars, TimeType}

object SeatunnelEnvConfiguration {
  val SEATUNNEL_HOME: CommonVars[String] = CommonVars("wds.linkis.engine.seatunnel.plugin.home", "/opt/linkis/seatunnel")
  val SEATUNNEL_STATUS_FETCH_INTERVAL: CommonVars[TimeType] = CommonVars("seatunnel.fetch.status.interval", new TimeType("5s"))

}
