package com.shoggoth;

import java.sql.*;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/jdbc_practical_task_db";
    static final String USER = "admin";
    static final String PASSWORD = "admin";

    static final String GET_DEVELOPERS_SQL = """
            SELECT * FROM developer;
            """;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection;
        Statement statement;
            System.out.println("Registering JDBC driver...");
            Class.forName(JDBC_DRIVER);
            System.out.println("Creating connection...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        System.out.println(connection);
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_DEVELOPERS_SQL);
        while (resultSet.next()) {
            System.out.printf("%d   %s  %s  %d  %d\n",
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("specialty_id"),
                    resultSet.getInt("status_id"));

        }

    }
}