package org.apache.linkis.metadatamanager.service;

import com.webank.wedatasphere.linkis.metadatamanager.common.domain.MetaColumnInfo;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SqlConnectionTestCase {

    @Test
    public void testGetDatabases() throws SQLException, ClassNotFoundException {
        SqlConnection connection = new SqlConnection("192.168.0.71", 54321, "TESTUSER", "test!0819", "my_db", new HashMap<>());
        List<String> allDatabases = connection.getAllDatabases();
        System.out.println(allDatabases);
        Assert.assertNotNull(allDatabases);
    }

    @Test
    public void testGetCurrentDatabaseSchemaUserTables() throws SQLException, ClassNotFoundException {
        SqlConnection connection = new SqlConnection("192.168.0.71", 54321, "TESTUSER", "test!0819", "my_db", new HashMap<>());
        List<String> allTables = connection.getAllTables("public");
        System.out.println(allTables);
        Assert.assertNotNull(allTables);
    }

    @Test
    public void testGetColumns() throws SQLException, ClassNotFoundException {
        SqlConnection connection = new SqlConnection("192.168.0.71", 54321, "TESTUSER", "test!0819", "my_db", new HashMap<>());
        List<MetaColumnInfo> columns = connection.getColumns("public", "pathman_config_params");
        for (MetaColumnInfo column : columns) {
            System.out.println(column.getIndex() + "\t" + column.getName() + "\t" + column.getType());
        }
        Assert.assertNotNull(columns);
    }

}
