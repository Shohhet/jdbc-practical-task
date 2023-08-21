package com.shoggoth.model.repository.impl;

import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.repository.SpecialtyRepository;

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
    public Optional<Specialty> add(Specialty specialty) throws RepositoryException {
        Specialty returnedSpecialty = null;
        Optional<Specialty> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(ADD_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, specialty.getName());
                prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    returnedSpecialty = new Specialty();
                    returnedSpecialty.setId(resultSet.getLong(1));
                    returnedSpecialty.setName(resultSet.getString(2));
                    returnedSpecialty.setStatus(Status.ACTIVE);
                    System.out.println(specialty);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); //TODO Add logger
        }

        return Optional.ofNullable(returnedSpecialty);
    }

    @Override
    public Optional<Specialty> getById(Long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public Optional<Specialty> update(Specialty specialty) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        return false;
    }
}
