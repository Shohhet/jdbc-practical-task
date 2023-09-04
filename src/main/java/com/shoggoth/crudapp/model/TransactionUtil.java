package com.shoggoth.crudapp.model;

import com.shoggoth.crudapp.model.repository.GenericRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {
    Connection connection;

    //TODO: think -> try to get rid off mutable repo connection
    public void begin(GenericRepository repository, GenericRepository... repositories) throws SQLException {
        connection = ConnectionUtils.getConnection();
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
