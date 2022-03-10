package org.apache.linkis.computation.client;

import org.apache.linkis.computation.client.interactive.SubmittableInteractiveJob;

public class DataSourceOnceJobTest {

    public static void main(String[] args) {
        // TODO First, set the right gateway url.
        LinkisJobClient.config().setDefaultServerUrl("http://127.0.0.1:9002");
        //TODO Secondly, please modify the executeUser
        SubmittableInteractiveJob job = LinkisJobClient.interactive().builder()
                .setEngineType("hive").setRunTypeStr("sql").setCreator("IDE")
                .setCode("show tables").addExecuteUser("hadoop").build();
        // 3. Submit Job to Linkis
        job.submit();
        // 4. Wait for Job completed
        job.waitForCompleted();
        // 5. Get results from iterators.
        ResultSetIterator iterator = job.getResultSetIterables()[0].iterator();
        System.out.println(iterator.getMetadata());
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
