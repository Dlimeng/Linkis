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
        SqlConnection connection = new SqlConnection("192.168.0.71", 1521, "qbitdata", "test!071471", "ORCL", new HashMap<>());
        List<String> allDatabases = connection.getAllDatabases();
        System.out.println(allDatabases);
        Assert.assertNotNull(allDatabases);
    }

    @Test
    public void testGetCurrentDatabaseSchemaUserTables() throws SQLException, ClassNotFoundException {
        SqlConnection connection = new SqlConnection("192.168.0.71", 1521, "qbitdata", "test!071471", "ORCL", new HashMap<>());
        List<String> allTables = connection.getAllTables("QBITDATA");
        System.out.println(allTables);
        Assert.assertNotNull(allTables);
    }

    @Test
    public void testGetColumns() throws SQLException, ClassNotFoundException {
        SqlConnection connection = new SqlConnection("192.168.0.71", 1521, "qbitdata", "test!071471", "ORCL", new HashMap<>());
        List<MetaColumnInfo> columns = connection.getColumns("QBITDATA", "STUDENT_ORC");
        for (MetaColumnInfo column : columns) {
            System.out.println(column.getIndex() + "\t" + column.getName() + "\t" + column.getType());
        }
        Assert.assertNotNull(columns);
    }

}
