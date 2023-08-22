package com.shoggoth.model.repository.impl;

import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.repository.DeveloperRepository;

import java.sql.*;
import java.util.Optional;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private final Connection connection;

    public JdbcDeveloperRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String ADD_DEVELOPER_SQL = """
            INSERT INTO developer (first_name, last_name, status)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE status = ?;
            """;
    private static final String ADD_DEVELOPER_SKILL_SQL = """
            INSERT INTO developer_skill (developer_id, skill_id)
            VALUES (?, ?);
            """;

    public static final String DELETE_DEVELOPER_SQL = """
            UPDATE developer
            SET status = 'DELETED'
            WHERE id = ? AND status LIKE 'ACTIVE';

            """;
    private static final String DELETE_DEVELOPER_SKILL_SQL = """
            DELETE FROM developer_skill
            WHERE developer_id = ?;
            """;

    private static final String GET_DEVELOPER_SQL = """
            SELECT developer.id, first_name, last_name, specialty.name
            FROM developer JOIN specialty ON developer.specialty_id = specialty.id
            WHERE developer.id = ?;
            """;
    private static final String GET_DEVELOPER_SKILL_SQL = """
            SELECT name
            FROM developer_skill JOIN skill ON developer_skill.skill_id = skill.id
            WHERE developer_skill.developer_id = ?;
            """;


    @Override
    public Optional<Developer> add(Developer developer) throws RepositoryException {
        Optional<Developer> maybeDeveloper = Optional.empty();
        try(var prepStatement = connection.prepareStatement(ADD_DEVELOPER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, developer.getFirstName());
            prepStatement.setString(2, developer.getLastName());
            prepStatement.setString(3, Status.ACTIVE.name());
            prepStatement.setString(4, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try(var resultSet = prepStatement.getGeneratedKeys()){
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
        return Optional.empty();
    }

    @Override
    public Optional<Developer> update(Developer developer) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        return false;
    }
}
