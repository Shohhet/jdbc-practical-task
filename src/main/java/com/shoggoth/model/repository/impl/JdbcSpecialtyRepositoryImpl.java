package com.shoggoth.model.repository.impl;

import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.repository.SpecialtyRepository;
import com.shoggoth.model.repository.mapper.impl.DbRowToSpecialtyMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    private static final String ADD_SPECIALTY_SQL = """
            INSERT INTO specialty (name, status)
            VALUES (?, ?)
            ON DUPLICATE KEY UPDATE status = ?;
            """;

    private static final String DELETE_SPECIALTY_SQL = """
            UPDATE specialty
            SET status = ?
            WHERE id = ? AND status LIKE ?;
            """;

    private static final String GET_SPECIALTY_BY_ID_SQL = """
            SELECT id, name, status
            FROM specialty
            WHERE id = ? AND status LIKE ?;
            """;

    private static final String UPDATE_SPECIALTY_SQL = """
            UPDATE specialty
            SET name = ?
            WHERE id = ? AND status LIKE ?;
            """;

    private static final String GET_ALL_SPECIALTY_SQL = """
            SELECT id, name, status
            FROM specialty
            WHERE status = ?;
            """;

    private static final String GET_SPECIALTY_BY_NAME_SQL = """
            SELECT id, name, status
            FROM specialty
            WHERE name LIKE ? AND status LIKE ?;
            """;
    private Connection connection;


    @Override
    public Optional<Specialty> add(Specialty specialty) throws RepositoryException {
        Optional<Specialty> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(ADD_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, specialty.getName());
            prepStatement.setString(2, Status.ACTIVE.name());
            prepStatement.setString(3, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    specialty.setId(resultSet.getLong(1));
                    specialty.setStatus(Status.ACTIVE);
                    maybeSpecialty = Optional.of(specialty);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); //TODO Add logger
        }
        return maybeSpecialty;
    }

    @Override
    public Optional<Specialty> getById(Long id) throws RepositoryException {
        Optional<Specialty> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_SPECIALTY_BY_ID_SQL)) {
            prepStatement.setLong(1, id);
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    maybeSpecialty = DbRowToSpecialtyMapper.getInstance().map(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return maybeSpecialty;
    }

    @Override
    public Optional<Specialty> update(Specialty specialty) throws RepositoryException {
        Optional<Specialty> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(UPDATE_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, specialty.getName());
            prepStatement.setLong(2, specialty.getId());
            prepStatement.setString(3, Status.ACTIVE.name());
            int affectedRows = prepStatement.executeUpdate();
            if (affectedRows == 1) {
                specialty.setStatus(Status.ACTIVE);
                maybeSpecialty = Optional.of(specialty);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); //TODO Add logger
        }
        return maybeSpecialty;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(DELETE_SPECIALTY_SQL)) {
            prepStatement.setString(1, Status.DELETED.name());
            prepStatement.setLong(2, id);
            prepStatement.setString(3, Status.ACTIVE.name());
            int affectedRows = prepStatement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
    }

    @Override
    public List<Specialty> getAll() throws RepositoryException {
        List<Specialty> specialtyList = new ArrayList<>();
        try (var prepStatement = connection.prepareStatement(GET_ALL_SPECIALTY_SQL)) {
            prepStatement.setString(1, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    DbRowToSpecialtyMapper
                            .getInstance()
                            .map(resultSet)
                            .ifPresent(specialtyList::add);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return specialtyList;
    }

    @Override
    public Optional<Specialty> getByName(Specialty specialty) throws RepositoryException {
        Optional<Specialty> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_SPECIALTY_BY_NAME_SQL)) {
            prepStatement.setString(1, specialty.getName());
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    maybeSpecialty = DbRowToSpecialtyMapper.getInstance().map(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); // TODO Add logger
        }
        return maybeSpecialty;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
