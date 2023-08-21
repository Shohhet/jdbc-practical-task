package com.shoggoth.repository.impl;

import com.shoggoth.exceptions.RepositoryException;
import com.shoggoth.entity.Skill;
import com.shoggoth.repository.SkillRepository;

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
    public Optional<Skill> add(Skill skill) {

        try {
            var preparedStatement = connection.prepareStatement(ADD_SKILL_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, skill.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                skill.setId(generatedKeys.getLong(1));
            }
            return skill;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try {
            var preparedStatement = connection.prepareStatement(DELETE_SKILL_SQL);
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement = connection.prepareStatement(DELETE_SKILL_FOR_DEVELOPERS_SQL);
                preparedStatement.setLong(1, id);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Skill skill) throws RepositoryException {
        try {
            var preparedStatement = connection.prepareStatement(UPDATE_SKILL_SQL);
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<Skill> getById(Long id) {
        Skill skill = null;
        try {
            var preparedStatement = connection.prepareStatement(GET_SKILL_SQL);
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                skill = new Skill(id, resultSet.getString("name"));
            }
            return skill;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
