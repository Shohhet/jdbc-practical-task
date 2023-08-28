package com.shoggoth.model.repository.impl;

import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.repository.DeveloperRepository;
import com.shoggoth.model.repository.mapper.impl.DbRowToDeveloperMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private Connection connection;

    private static final String ADD_DEVELOPER_SQL = """
            INSERT INTO developer (first_name, last_name, specialty_id, status)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE status = ?;
            """;
    private static final String UPDATE_DEVELOPER_SQL = """
            UPDATE developer
            SET first_name = ?, last_name = ?, specialty_id = ?
            WHERE id = ? AND status = ?;
            """;
    private static final String ADD_DEVELOPER_SKILL_SQL = """
            INSERT INTO developer_skill (developer_id, skill_id)
            VALUES (?, ?);
            """;

    public static final String DELETE_DEVELOPER_SQL = """
            UPDATE developer
            SET status = ?
            WHERE id = ? AND status LIKE ?;
            """;
    private static final String DELETE_DEVELOPER_SKILL_SQL = """
            DELETE FROM developer_skill
            WHERE developer_id = ?;
            """;

    private static final String GET_DEVELOPER_SQL = """
            SELECT developer.id, developer.first_name, developer.last_name, developer.specialty_id, specialty.name, specialty.status, developer.status
            FROM developer INNER JOIN specialty ON developer.specialty_id = specialty.id
            WHERE developer.id = ? AND developer.status = ? AND specialty.status = ?;
            """;
    private static final String GET_ALL_DEVELOPER_SQL = """
            SELECT id, first_name, last_name, status
            FROM developer
            WHERE status = ?;
            """;
    private static final String GET_DEVELOPER_SKILL_SQL = """
            SELECT name
            FROM developer_skill JOIN skill ON developer_skill.skill_id = skill.id
            WHERE developer_skill.developer_id = ?;
            """;

    private static final String SET_NULL_SPECIALTY_FOR_DEVELOPERS_SQL = """
            UPDATE developer
            SET specialty_id = NULL
            WHERE specialty_id = ?
            """;


    @Override
    public Optional<Developer> add(Developer developer) throws RepositoryException {
        Optional<Developer> maybeDeveloper = Optional.empty();
        try (var prepStatement = connection.prepareStatement(ADD_DEVELOPER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, developer.getFirstName());
            prepStatement.setString(2, developer.getLastName());
            prepStatement.setLong(3, developer.getSpecialty().getId());
            prepStatement.setString(4, Status.ACTIVE.name());
            prepStatement.setString(5, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    developer.setId(resultSet.getLong(1));
                    developer.setStatus(Status.ACTIVE);
                    maybeDeveloper = Optional.of(developer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO Add logger
        }
        return maybeDeveloper;
    }

    @Override
    public Optional<Developer> getById(Long id) throws RepositoryException {
        Optional<Developer> maybeDeveloper = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_DEVELOPER_SQL)) {
            prepStatement.setLong(1, id);
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    maybeDeveloper = DbRowToDeveloperMapper.getInstance().map(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return maybeDeveloper;
    }

    @Override
    public Optional<Developer> update(Developer developer) throws RepositoryException {
        Optional<Developer> maybeDeveloper = Optional.empty();
        try (var prepStatement = connection.prepareStatement(UPDATE_DEVELOPER_SQL)) {
            prepStatement.setString(1, developer.getFirstName());
            prepStatement.setString(2, developer.getLastName());
            prepStatement.setLong(3, developer.getId());
            prepStatement.setString(4, Status.ACTIVE.name());
            int affectedRows = prepStatement.executeUpdate();
            if (affectedRows == 1) {
                developer.setStatus(Status.ACTIVE);
                maybeDeveloper = Optional.of(developer);
            }
        } catch (
                SQLException e) {
            throw new RepositoryException(e); //TODO Add logger
        }
        return maybeDeveloper;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(DELETE_DEVELOPER_SQL)) {
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
    public List<Developer> getAll() throws RepositoryException {
        List<Developer> developerList = new ArrayList<>();
        try (var prepStatement = connection.prepareStatement(GET_ALL_DEVELOPER_SQL)) {
            prepStatement.setString(1, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    DbRowToDeveloperMapper
                            .getInstance()
                            .map(resultSet)
                            .ifPresent(developerList::add);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return developerList;
    }

    @Override
    public boolean deleteSpecialtyForDevelopers(Long id) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(SET_NULL_SPECIALTY_FOR_DEVELOPERS_SQL)) {
            prepStatement.setLong(1, id);
            int affectedRow = prepStatement.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e); // TODO Add logger
        }
    }

        @Override
    public void addDeveloperSkill(Long developerId, Long skillId) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(ADD_DEVELOPER_SKILL_SQL)) {
            prepStatement.setLong(1, developerId);
            prepStatement.setLong(2, skillId);
            int affectedRows = prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
