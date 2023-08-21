package com.shoggoth.repository.impl;

import com.shoggoth.exceptions.RepositoryException;
import com.shoggoth.entity.Specialty;
import com.shoggoth.repository.SpecialtyRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    private static final String ADD_SPECIALTY_SQL = """
            INSERT INTO specialty (name, status)
            VALUES (?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE status = 'ACTIVE';
            """;

    private static final String DELETE_SPECIALTY_SQL = """
            UPDATE specialty
            SET status = 'DELETE'
            WHERE id = ? AND status LIKE 'ACTIVE';
            """;

    private static final String GET_SPECIALTY_NAME_SQL = """
            SELECT name
            FROM specialty
            WHERE id = ? AND status LIKE 'ACTIVE';
            """;

    private static final String UPDATE_SPECIALTY_SQL = """
            UPDATE specialty
            SET name = ?
            WHERE id = ? AND status LIKE 'ACTIVE';
            """;
    private static final String GET_SPECIALTY_ID_SQL = """
            SELECT id
            FROM specialty
            WHERE name = ?;           
            """;


    private final Connection connection;

    public JdbcSpecialtyRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Specialty> add(Specialty specialty) {
        try {
            var preparedStatement = connection.prepareStatement(ADD_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                specialty.setId(generatedKeys.getLong(1));
            } else {
                specialty = getByName(specialty.getName());
            }
            return specialty;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try {
            var preparedStatement = connection.prepareStatement(DELETE_SPECIALTY_SQL);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Specialty specialty) throws RepositoryException {
        try {
            var preparedStatement = connection.prepareStatement(UPDATE_SPECIALTY_SQL);
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.setLong(2, specialty.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<Specialty> getById(Long id) {
        Specialty specialty = null;
        try {
            var preparedStatement = connection.prepareStatement(GET_SPECIALTY_NAME_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                specialty = new Specialty(id, resultSet.getString("name"));
            }
            return specialty;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private Specialty getByName(String name) {
        Specialty specialty = null;
        try {
            var preparedStatement = connection.prepareStatement(GET_SPECIALTY_ID_SQL);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                specialty = new Specialty(resultSet.getLong("id"), name);
            }
            return specialty;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

}
