package com.shoggoth.model.repository.impl;

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
            INSERT INTO developer (id, first_name, last_name, specialty_id, status)
            VALUES (?, ?, ?, ?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE status = 'ACTIVE';
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
        return Optional.empty();
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
