package com.shoggoth.repository;

import com.shoggoth.exceptions.RepositoryException;
import com.shoggoth.pojo.Skill;
import com.shoggoth.repository.interfaces.SkillRepository;

import java.sql.*;
import java.util.Collection;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    public static final String ADD_SKILL_SQL = """
            INSERT INTO skill (skill_name, skill_status)
            VALUES(?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE skill_status = 'ACTIVE';
            """;

    public static final String DELETE_SKILL_SQL = """
            UPDATE skill
            SET skill_status = 'DELETE'
            WHERE id = ? AND (SELECT skill_id FROM developer_skill WHERE skill_id = ?) IS NULL;
            """;

    public static final String GET_SKILL_SQL = """
            SELECT skill_name
            FROM skill
            WHERE  id = ? AND skill_status = 'ACTIVE';
            """;
    private final Connection connection;

    public JdbcSkillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Skill add(Skill skill) {
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
    public boolean delete(Long id) {
        try {
            var preparedStatement = connection.prepareStatement(DELETE_SKILL_SQL);
            preparedStatement.setLong(1, id);
            return  preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Long id, Skill skill) {
        return false;
    }

    @Override
    public Skill getById(Long id) {
        Skill skill;
        try {
            var preparedStatement = connection.prepareStatement(GET_SKILL_SQL);
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            skill = new Skill(resultSet.getLong("id"), resultSet.getString("skill_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    @Override
    public Collection<Skill> getAll() {
        return null;
    }
}
