package com.shoggoth.crudapp.model.repository.impl;

import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.repository.SpecialtyRepository;
import com.shoggoth.crudapp.model.repository.mapper.impl.DbRowToSpecialtyMapper;

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
    public Optional<SpecialtyEntity> add(SpecialtyEntity specialtyEntity) throws RepositoryException {
        Optional<SpecialtyEntity> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(ADD_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, specialtyEntity.getName());
            prepStatement.setString(2, Status.ACTIVE.name());
            prepStatement.setString(3, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    specialtyEntity.setId(resultSet.getLong(1));
                    specialtyEntity.setStatus(Status.ACTIVE);
                    maybeSpecialty = Optional.of(specialtyEntity);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); //TODO Add logger
        }
        return maybeSpecialty;
    }

    @Override
    public Optional<SpecialtyEntity> getById(Long id) throws RepositoryException {
        Optional<SpecialtyEntity> maybeSpecialty = Optional.empty();
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
    public Optional<SpecialtyEntity> update(SpecialtyEntity specialtyEntity) throws RepositoryException {
        Optional<SpecialtyEntity> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(UPDATE_SPECIALTY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, specialtyEntity.getName());
            prepStatement.setLong(2, specialtyEntity.getId());
            prepStatement.setString(3, Status.ACTIVE.name());
            int affectedRows = prepStatement.executeUpdate();
            if (affectedRows == 1) {
                specialtyEntity.setStatus(Status.ACTIVE);
                maybeSpecialty = Optional.of(specialtyEntity);
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
    public List<SpecialtyEntity> getAll() throws RepositoryException {
        List<SpecialtyEntity> specialtyEntityList = new ArrayList<>();
        try (var prepStatement = connection.prepareStatement(GET_ALL_SPECIALTY_SQL)) {
            prepStatement.setString(1, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    DbRowToSpecialtyMapper
                            .getInstance()
                            .map(resultSet)
                            .ifPresent(specialtyEntityList::add);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return specialtyEntityList;
    }

    @Override
    public Optional<SpecialtyEntity> getByName(SpecialtyEntity specialtyEntity) throws RepositoryException {
        Optional<SpecialtyEntity> maybeSpecialty = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_SPECIALTY_BY_NAME_SQL)) {
            prepStatement.setString(1, specialtyEntity.getName());
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
