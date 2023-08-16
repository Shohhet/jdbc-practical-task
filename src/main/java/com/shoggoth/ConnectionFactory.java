package com.shoggoth;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class ConnectionFactory {
 /*   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/jdbc_practical_task_db";
    static final String USER = "admin";
    static final String PASSWORD = "admin";*/

    public static Connection getConnection() {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        Properties properties = new Properties();
        MysqlDataSource mysqlDataSource;
        Connection connection;

        try (FileInputStream fileInputStream = new FileInputStream(path + "db.properties")){
            properties.load(fileInputStream);
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
            connection = mysqlDataSource.getConnection();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
