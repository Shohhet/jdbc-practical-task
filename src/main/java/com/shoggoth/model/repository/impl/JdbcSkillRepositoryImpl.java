package com.shoggoth.model.repository.impl;

import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.repository.SkillRepository;

import java.sql.*;
import java.util.Optional;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    private static final String ADD_SKILL_SQL = """
            INSERT INTO skill (name, status)
            VALUES(?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE status = 'ACTIVE';
            """;

    private static final String DELETE_SKILL_SQL = """
            UPDATE skill
            SET status = 'DELETE'
            WHERE id = ? AND status LIKE 'ACTIVE';
            """;

    private static final String DELETE_SKILL_FOR_DEVELOPERS_SQL = """
            DELETE FROM developer_skill
            WHERE skill_id = ?;
            """;

    private static final String GET_SKILL_SQL = """
            SELECT name
            FROM skill
            WHERE  id = ? AND status = 'ACTIVE';
            """;

    private static final String UPDATE_SKILL_SQL = """
            UPDATE skill
            SET name = ?
            WHERE id = ? AND status LIKE 'ACTIVE';
            """;
    private final Connection connection;

    public JdbcSkillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<Skill> add(Skill skill) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public Optional<Skill> getById(Long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public Optional<Skill> update(Skill skill) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        return false;
    }
}
