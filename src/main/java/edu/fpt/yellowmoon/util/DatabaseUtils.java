package edu.fpt.yellowmoon.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static DataSource getDataSource() {
        return DataSourceHolder.dataSource;
    }

    public static Connection getConnection() {
        try {
            return DataSourceHolder.dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Getting Database Connection Failed", e);
        }
        return null;
    }

    private static class DataSourceHolder {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/yellow_moon";
        private static final String USER = "root";
        private static final String PASS = "2611";
        private static final BasicDataSource dataSource;

        static {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(DB_URL);
            dataSource.setUsername(USER);
            dataSource.setPassword(PASS);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(100);
            dataSource.setRemoveAbandonedOnBorrow(true);
            dataSource.setRemoveAbandonedOnMaintenance(true);
        }
    }
}
