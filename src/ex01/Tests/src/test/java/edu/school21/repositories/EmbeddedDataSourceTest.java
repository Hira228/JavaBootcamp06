package edu.school21.repositories;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


import javax.sql.DataSource;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    DataSource dataSource = null;
    @BeforeEach
    public void init() {
        dataSource =  new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

    }

    @Test
    public void testConnection() throws SQLException {
        Assertions.assertNotNull(dataSource.getConnection());
    }
}
