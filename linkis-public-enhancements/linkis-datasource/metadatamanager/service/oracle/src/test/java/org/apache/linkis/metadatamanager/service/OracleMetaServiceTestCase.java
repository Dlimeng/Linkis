package org.apache.linkis.metadatamanager.service;

import com.webank.wedatasphere.linkis.metadatamanager.common.domain.MetaColumnInfo;
import com.webank.wedatasphere.linkis.metadatamanager.common.service.MetadataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleMetaServiceTestCase {
    private Map<String, Object> params;
    private MetadataService metadataService;

    @Before
    public void before() {
        params = new HashMap<>();
        params.put("host", "192.168.0.71");
        params.put("port", 1521);
        params.put("username", "qbitdata");
        params.put("password", "test!071471");
        params.put("database", "ORCL");

        metadataService = new OracleMetaService();
    }

    @Test
    public void testGetDatabases() {
        List<String> databases = metadataService.getDatabases("", params);
        System.out.println(databases);
        Assert.assertNotNull(databases);
    }

    @Test
    public void testGetTables() {
        // 这里的 public 参数是 pg 中的 schema
        List<String> tables = metadataService.getTables("", params, "QBITDATA");
        System.out.println(tables);
        Assert.assertNotNull(tables);
    }

    @Test
    public void testGetColumns() {
        List<MetaColumnInfo> columns = metadataService.getColumns("", params, "QBITDATA", "STUDENT_ORC");
        for (MetaColumnInfo column : columns) {
            System.out.println(column.getIndex() + "\t" + column.getName() + "\t" + column.getType());
        }
        Assert.assertNotNull(columns);
    }
}
