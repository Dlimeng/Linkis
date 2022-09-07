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
import org.apache.seatunnel.core.spark.SeatunnelSpark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;

public class LinkisSeatunnelFlinkSQLClient {
    private static Logger logger = LoggerFactory.getLogger(LinkisSeatunnelSparkClient.class);
    private static Class<?> seatunnelEngineClass;
    private static JarLoader jarLoader;

    public static int main(String[] args) {
        try {
            jarLoader = new JarLoader(new String[]{
                    LinkisSeatunnelSparkClient.class.getProtectionDomain().getCodeSource().getLocation().getPath()
            });
            seatunnelEngineClass = jarLoader.loadClass("org.apache.seatunnel.core.spark.SparkStarter");
            jarLoader.addJarURL(SeatunnelSpark.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            Thread.currentThread().setContextClassLoader(jarLoader);
            Method method = seatunnelEngineClass.getDeclaredMethod("main",String[].class);
            return (Integer) method.invoke(null, (Object) args);
        }catch (Throwable e){
            logger.error("Run Error Message:"+getLog(e));
            return -1;
        }
    }

    private static String getLog(Throwable e){
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return e.toString();
    }
}
