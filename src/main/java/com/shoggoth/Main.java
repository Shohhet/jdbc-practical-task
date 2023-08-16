package com.shoggoth;

import java.sql.*;

public class Main {
    static final String GET_DEVELOPERS_SQL = """
            SELECT * FROM developer;
            """;

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
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