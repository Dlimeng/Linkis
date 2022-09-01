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

package org.apache.linkis.engineconnplugin.seatunnel.client;

import org.apache.linkis.engineconnplugin.seatunnel.client.utils.JarLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkisSeatunnelSparkClient {
    private static Logger logger = LoggerFactory.getLogger(LinkisSeatunnelSparkClient.class);
    private static Class<?> seatunnelEngineClass;
    private static JarLoader jarLoader;

    public static int main(String[] args) {
        return 0;
    }
}
