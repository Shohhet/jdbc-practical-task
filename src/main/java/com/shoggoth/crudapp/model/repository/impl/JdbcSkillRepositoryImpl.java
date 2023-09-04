package com.shoggoth.crudapp.model.repository.impl;

import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.repository.mapper.impl.DbRowToSkillMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    private static final String ADD_SKILL_SQL = """
            INSERT INTO skillEntity (name, status)
            VALUES(?, ?)
            ON DUPLICATE KEY UPDATE status = ?;
            """;

    private static final String DELETE_SKILL_SQL = """
            UPDATE skillEntity
            SET status = ?
            WHERE id = ? AND status LIKE ?;
            """;

    private static final String DELETE_SKILL_FOR_DEVELOPERS_SQL = """
            DELETE FROM developer_skill
            WHERE skill_id = ?;
            """;

    private static final String GET_SKILL_BY_ID_SQL = """
            SELECT id, name, status
            FROM skillEntity
            WHERE  id = ? AND status = ?;
            """;

    private static final String UPDATE_SKILL_SQL = """
            UPDATE skillEntity
            SET name = ?
            WHERE id = ? AND status LIKE ?;
            """;
    private static final String GET_ALL_SKILL_SQL = """
            SELECT id, name, status
            FROM skillEntity
            WHERE status = ?;
            """;

    private static final String GET_SKILL_BY_NAME_SQL = """
            SELECT id, name, status
            FROM skillEntity
            WHERE name LIKE ? AND status LIKE ?;
            """;
    private static final String GET_SKILLS_BY_DEVELOPER_ID_SQL = """
            SELECT id, name, status
            FROM developer_skill INNER JOIN skillEntity ON developer_skill.skill_id = skillEntity.id
            WHERE developer_id = ? AND status = ?;
            """;

    private Connection connection;

    @Override
    public Optional<SkillEntity> add(SkillEntity skillEntity) throws RepositoryException {
        Optional<SkillEntity> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(ADD_SKILL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, skillEntity.getName());
            prepStatement.setString(2, Status.ACTIVE.name());
            prepStatement.setString(3, Status.ACTIVE.name());
            prepStatement.executeUpdate();
            try (var resultSet = prepStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    skillEntity.setId(resultSet.getLong(1));
                    skillEntity.setStatus(Status.ACTIVE);
                    maybeSkill = Optional.of(skillEntity);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeSkill;
    }

    @Override
    public Optional<SkillEntity> getById(Long id) throws RepositoryException {
        Optional<SkillEntity> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_SKILL_BY_ID_SQL)) {
            prepStatement.setLong(1, id);
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    maybeSkill = DbRowToSkillMapper.getInstance().map(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Add logger
        }
        return maybeSkill;
    }

    @Override
    public Optional<SkillEntity> update(SkillEntity skillEntity) throws RepositoryException {
        Optional<SkillEntity> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(UPDATE_SKILL_SQL)) {
            prepStatement.setString(1, skillEntity.getName());
            prepStatement.setLong(2, skillEntity.getId());
            prepStatement.setString(3, Status.ACTIVE.name());
            int affectedRows = prepStatement.executeUpdate();
            if (affectedRows == 1) {
                skillEntity.setStatus(Status.ACTIVE);
                maybeSkill = Optional.of(skillEntity);
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
    public void deleteForDevelopers(Long id) throws RepositoryException {
        try (var prepStatement = connection.prepareStatement(DELETE_SKILL_FOR_DEVELOPERS_SQL)) {
            prepStatement.setLong(1, id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
    }

    @Override
    public List<SkillEntity> getAll() throws RepositoryException {
        List<SkillEntity> skillEntityList = new ArrayList<>();
        try (var prepStatement = connection.prepareStatement(GET_ALL_SKILL_SQL)) {
            prepStatement.setString(1, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    DbRowToSkillMapper
                            .getInstance()
                            .map(resultSet)
                            .ifPresent(skillEntityList::add);
                }

            }
        } catch (SQLException e) {
            throw new RepositoryException(e); // TODO Add logger
        }
        return skillEntityList;
    }

    @Override
    public Optional<SkillEntity> getByName(SkillEntity skillEntity) throws RepositoryException {
        Optional<SkillEntity> maybeSkill = Optional.empty();
        try (var prepStatement = connection.prepareStatement(GET_SKILL_BY_NAME_SQL)) {
            prepStatement.setString(1, skillEntity.getName());
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    maybeSkill = DbRowToSkillMapper.getInstance().map(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e); // TODO Add logger
        }
        return maybeSkill;
    }

    @Override
    public List<SkillEntity> getDeveloperSkills(Long id) throws RepositoryException {
        List<SkillEntity> skillEntityList = new ArrayList<>();
        try (var prepStatement = connection.prepareStatement(GET_SKILLS_BY_DEVELOPER_ID_SQL)) {
            prepStatement.setLong(1, id);
            prepStatement.setString(2, Status.ACTIVE.name());
            try (var resultSet = prepStatement.executeQuery()) {
                while (resultSet.next()) {
                    DbRowToSkillMapper
                            .getInstance()
                            .map(resultSet)
                            .ifPresent(skillEntityList::add);
                }

            }
        } catch (SQLException e) {
            throw new RepositoryException(e); // TODO Add logger
        }
        return skillEntityList;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
