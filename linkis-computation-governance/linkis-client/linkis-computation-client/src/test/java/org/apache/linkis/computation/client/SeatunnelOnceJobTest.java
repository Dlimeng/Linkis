package org.apache.linkis.computation.client;

import org.apache.linkis.common.conf.Configuration;
import org.apache.linkis.computation.client.once.simple.SubmittableSimpleOnceJob;
import org.apache.linkis.computation.client.utils.LabelKeyUtils;

public class SeatunnelOnceJobTest {
    public static void main(String[] args) {
        LinkisJobClient.config().setDefaultServerUrl("http://192.168.0.27:9001");
        String code ="env{spark.app.name=\"SeaTunnel\"spark.executor.instances=2spark.executor.cores=1spark.executor.memory=\"1g\"}source{Fake{result_table_name=\"my_dataset\"}}transform{}sink{Console{}}";
        SubmittableSimpleOnceJob onceJob = LinkisJobClient.once().simple().builder().setCreateService("seatunnel-Test")
                .setMaxSubmitTime(300000)
                .addLabel(LabelKeyUtils.ENGINE_TYPE_LABEL_KEY(), "seatunnel-2.1.2")
                .addLabel(LabelKeyUtils.USER_CREATOR_LABEL_KEY(), "hadoop-seatunnel")
                .addLabel(LabelKeyUtils.ENGINE_CONN_MODE_LABEL_KEY(), "once")
                .addStartupParam(Configuration.IS_TEST_MODE().key(), true)
                .addExecuteUser("hadoop")
                .addJobContent("runType", "sspark")
                .addJobContent("code", code)
                .addSource("jobName", "OnceJobTest")
                .build();
        onceJob.submit();
        System.out.println(onceJob.getId());

        onceJob.waitForCompleted();
        System.out.println(onceJob.getStatus());
        LinkisJobMetrics jobMetrics = onceJob.getJobMetrics();
        System.out.println(jobMetrics.getMetrics());
    }
}
