package org.apache.linkis.engineconnplugin.seatunnel.client;

public class LinkisSeatunnelSparkClientTest {
    public static void main(String[] args) {
        String[] str = {"--master","local[4]","--deploy-mode","client","--config","/opt"};
        LinkisSeatunnelSparkClient.main(str);
    }
}
