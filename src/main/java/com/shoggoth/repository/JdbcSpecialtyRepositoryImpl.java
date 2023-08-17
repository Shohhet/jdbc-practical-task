package com.shoggoth.repository;

import com.shoggoth.exceptions.RepositoryException;
import com.shoggoth.pojo.Specialty;
import com.shoggoth.repository.interfaces.SpecialtyRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    public static final String ADD_SPECIALTY_SQL = """
            INSERT INTO specialty (specialty_name, specialty_status)
            VALUES (?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE specialty_status = 'ACTIVE';
            """;

    public static final String DELETE_SPECIALTY_SQL = """
            UPDATE specialty
            SET specialty_status = 'DELETED'
            WHERE id = ? AND (SELECT specialty_id FROM developer WHERE specialty_id = ? AND developer_status = 'ACTIVE') IS NULL;
            """;

    public static final String GET_SPECIALTY_SQL = """
            SELECT specialty_name
            FROM specialty
            WHERE id = ? AND specialty_status = 'ACTIVE';
            """;
    private final Connection connection;

    public JdbcSpecialtyRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Specialty add(Specialty specialty) {
        try {
            var preparedStatement = connection.prepareStatement(ADD_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                specialty.setId(generatedKeys.getLong(1));
            }
            return specialty;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    @Override
    public boolean delete(Long id) {
        try {
            var preparedStatement = connection.prepareStatement(DELETE_SPECIALTY_SQL);
            preparedStatement.setLong(1, id);
            return  preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Long id, Specialty specialty) {
        return false;
    }

    @Override
    public Specialty getById(Long id) {
        try {
            var preparedStatement = connection.prepareStatement(GET_SPECIALTY_SQL);
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            return new Specialty(resultSet.getLong("id"), resultSet.getString("specialty_name"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    @Override
    public Collection<Specialty> getAll() {
        return null;
    }
}
