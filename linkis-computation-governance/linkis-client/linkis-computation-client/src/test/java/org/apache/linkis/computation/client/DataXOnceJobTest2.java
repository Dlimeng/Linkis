package org.apache.linkis.computation.client;

import com.google.gson.Gson;
import org.apache.linkis.common.conf.Configuration;
import org.apache.linkis.computation.client.once.simple.SubmittableSimpleOnceJob;
import org.apache.linkis.computation.client.utils.LabelKeyUtils;

import java.util.HashMap;
import java.util.Map;

public class DataXOnceJobTest2 {
    public static void main(String[] args) {
        LinkisJobClient.config().setDefaultServerUrl("http://192.168.0.25:8088");
        /**
         */
        String code = "{\"job\":{\"content\":[{\"reader\":{\"parameter\":{\"password\":\"rO0ABXQAC3NjbV9AY2FzYzJm\",\"column\":[{\"name\":\"id\",\"type\":\"VARCHAR\"},{\"name\":\"age\",\"type\":\"VARCHAR\"}],\"connection\":[{\"jdbcUrl\":[{\"database\":\"test\",\"port\":\"3306\",\"host\":\"192.168.0.21\"}],\"querySql\":[\"SELECT A.id,A.age FROM t_psn A\"]}],\"alias\":\"[\\\"A\\\"]\",\"table\":[\"t_psn\"],\"username\":\"scm\"},\"name\":\"mysqlreader\"},\"writer\":{\"parameter\":{\"path\":\"/user/hive/warehouse/test.db/t_psn\",\"hiveMetastoreUris\":\"thrift://192.168.0.21:9083\",\"column\":[{\"name\":\"id\",\"index\":0,\"type\":\"string\"},{\"name\":\"age\",\"index\":1,\"type\":\"string\"}],\"fileName\":\"exchangis_hive_w\",\"defaultFS\":\"hdfs://192.168.0.21:1429\",\"writeMode\":\"truncate\",\"fieldDelimiter\":\",\",\"fileType\":\"text\",\"hiveDatabase\":\"test\",\"haveKerberos\":false,\"hiveTable\":\"t_psn\",\"encoding\":\"UTF-8\",\"authType\":\"NONE\"},\"name\":\"hdfswriter\"}}],\"setting\":{\"syncMeta\":\"false\",\"errorLimit\":{\"record\":\"1\"},\"transport\":{\"type\":\"record\"},\"useProcessor\":\"false\",\"speed\":{\"byte\":\"10485760\",\"record\":\"10000\",\"channel\":\"0\"},\"advance\":{\"mMemory\":\"1024M\"}}}}";

        Map<String,String> rwMaps = new HashMap<>();
        rwMaps.put("reader","mysqlreader");
        rwMaps.put("writer","hdfswriter");
        SubmittableSimpleOnceJob onceJob = LinkisJobClient.once().simple().builder().setDescription(new Gson().toJson(rwMaps)).setCreateService("DataX-Test")
                .setMaxSubmitTime(300000)
                .addLabel(LabelKeyUtils.ENGINE_TYPE_LABEL_KEY(), "datax-3.0.0")
                .addLabel(LabelKeyUtils.USER_CREATOR_LABEL_KEY(), "hdfs-datax")
                .addLabel(LabelKeyUtils.ENGINE_CONN_MODE_LABEL_KEY(), "once")
                .addStartupParam(Configuration.IS_TEST_MODE().key(), true)
                .addExecuteUser("hdfs").addJobContent("runType", "appconn").addJobContent("code", code).addSource("jobName", "OnceJobTest")
                .build();
        onceJob.submit();
        System.out.println(onceJob.getId());

        onceJob.waitForCompleted();
        System.out.println(onceJob.getStatus());
        LinkisJobMetrics jobMetrics = onceJob.getJobMetrics();
        System.out.println(jobMetrics.getMetrics());
    }
}
