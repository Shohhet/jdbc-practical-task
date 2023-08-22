package com.shoggoth.model.repository.impl;

import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.repository.SkillRepository;
import com.shoggoth.model.repository.mapper.impl.DbRowToSkillMapper;

import java.sql.*;
import java.util.Optional;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    private static final String ADD_SKILL_SQL = """
            INSERT INTO skill (name, status)
            VALUES(?, ?)
            ON DUPLICATE KEY UPDATE status = ?;
            """;

    private static final String DELETE_SKILL_SQL = """
            UPDATE skill
            SET status = ?
            WHERE id = ? AND status LIKE ?;
            """;

    private static final String DELETE_SKILL_FOR_DEVELOPERS_SQL = """
            DELETE FROM developer_skill
            WHERE skill_id = ?;
            """;

    private static final String GET_SKILL_SQL = """
            SELECT id, name, status
            FROM skill
            WHERE  id = ? AND status = ?;
            """;

    private static final String UPDATE_SKILL_SQL = """
            UPDATE skill
            SET name = ?
            WHERE id = ? AND status LIKE ?;
            """;
    private final Connection connection;

    public JdbcSkillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<Skill> add(Skill skill) throws RepositoryException {
        Optional<Skill> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(ADD_SKILL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, skill.getName());
            prepStatement.setString(2, Status.ACTIVE.name());
            prepStatement.setString(3, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    skill.setId(resultSet.getLong(1));
                    skill.setStatus(Status.ACTIVE);
                    maybeSkill = Optional.of(skill);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeSkill;
    }

    @Override
    public Optional<Skill> getById(Long id) throws RepositoryException {
        Optional<Skill> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_SKILL_SQL)) {
            prepStatement.setLong(1, id);
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    maybeSkill = DbRowToSkillMapper.getInstance().map(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return maybeSkill;
    }

    @Override
    public Optional<Skill> update(Skill skill) throws RepositoryException {
        Optional<Skill> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(UPDATE_SKILL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, skill.getName());
            prepStatement.setLong(2, skill.getId());
            prepStatement.setString(3, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    skill.setId(resultSet.getLong(1));
                    skill.setStatus(Status.ACTIVE);
                    maybeSkill = Optional.of(skill);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); //TODO Add logger
        }
        return maybeSkill;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(DELETE_SKILL_SQL)) {
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
    public boolean deleteForDevelopers(Long id) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(DELETE_SKILL_FOR_DEVELOPERS_SQL)) {
            prepStatement.setLong(1, id);
            int affectedRows = prepStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
    }
}
