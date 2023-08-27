package com.shoggoth.model;

import com.shoggoth.model.repository.GenericRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {
    Connection connection;

    public void begin(GenericRepository repository, GenericRepository... repositories) throws SQLException {
        connection = ConnectionUtil.getConnection();
        connection.setAutoCommit(false);
        repository.setConnection(connection);
        for (GenericRepository repo : repositories) {
            repo.setConnection(connection);
        }
    }

    public void end() throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(true);
            connection.close();
        }
        connection = null;
    }

    public void commit() throws SQLException {
        if (connection != null) {
            connection.commit();
        }
    }

    public void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
        }
    }
}
