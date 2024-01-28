package edu.school21.repositories;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    JDBCDataSource dataSource = new JDBCDataSource();
    @BeforeEach
    public void init() {
        dataSource.setDatabase("jdbc:hsqldb:mem:testdb");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        EmbeddedDatabase embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder()
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
