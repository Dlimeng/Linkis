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

package org.apache.linkis.engineconnplugin.seatunnel

import org.apache.linkis.manager.engineplugin.common.EngineConnPlugin
import org.apache.linkis.manager.engineplugin.common.creation.EngineConnFactory
import org.apache.linkis.manager.engineplugin.common.launch.EngineConnLaunchBuilder
import org.apache.linkis.manager.engineplugin.common.resource.EngineResourceFactory
import org.apache.linkis.manager.label.entity.Label

import java.util

class SeatunnelEngineConnPlugin extends EngineConnPlugin{
  override def init(params: util.Map[String, Any]): Unit = ???

  override def getEngineResourceFactory: EngineResourceFactory = ???

  override def getEngineConnLaunchBuilder: EngineConnLaunchBuilder = ???

  override def getEngineConnFactory: EngineConnFactory = ???

  override def getDefaultLabels: util.List[Label[_]] = ???
}
