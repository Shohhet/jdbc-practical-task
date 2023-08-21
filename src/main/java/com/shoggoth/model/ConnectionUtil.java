package com.shoggoth.model;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionUtil {
    private ConnectionUtil() {
    }

    private static DataSource getDataSource() {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        Properties properties = new Properties();
        MysqlDataSource mysqlDataSource;
        try (FileInputStream fileInputStream = new FileInputStream(path + "db.properties")) {
            properties.load(fileInputStream);
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO Add logger
        }
        return mysqlDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();

    }

}
