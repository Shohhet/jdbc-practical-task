package com.shoggoth.repository;

import com.shoggoth.exceptions.RepositoryException;
import com.shoggoth.pojo.Developer;
import com.shoggoth.pojo.Skill;
import com.shoggoth.pojo.Specialty;
import com.shoggoth.repository.interfaces.DeveloperRepository;

import java.sql.*;
import java.util.Collection;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    Connection connection;

    public JdbcDeveloperRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String ADD_DEVELOPER_SQL = """
            INSERT INTO developer (id, first_name, last_name, specialty_id, developer_status)
            VALUES (?, ?, ?, ?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE developer_status = 'ACTIVE';
            """;
    private static final String ADD_DEVELOPER_SKILL_SQL = """
            INSERT INTO developer_skill (developer_id, skill_id)
            VALUES (?, ?, 'ACTIVE')
            ON DUPLICATE KEY UPDATE developer_skill_status = 'ACTIVE';
            """;

    public static final String DELETE_DEVELOPER_SQL = """
            UPDATE developer
            SET developer_status = 'DELETED'
            WHERE id = ?;

            """;
    private static final String DELETE_DEVELOPER_SKILL_SQL = """
            DELETE FROM developer_skill
            WHERE developer_id = ?;
            """;

    private static final String GET_DEVELOPER_SQL = """
            SELECT developer.id, first_name, last_name, specialty_name
            FROM developer JOIN specialty ON developer.specialty_id = specialty.id
            WHERE developer.id = ?;
            """;
    private static final String GET_DEVELOPER_SKILL_SQL = """
            SELECT skill_name
            FROM developer_skill JOIN skill ON developer_skill.skill_id = skill.id;
            """;

    @Override
    public Developer add(Developer developer) {
        try {
            var jdbcSpecialtyRepository = new JdbcSpecialtyRepositoryImpl(connection);
            developer.setSpecialty(jdbcSpecialtyRepository.add(developer.getSpecialty()));

            var preparedStatement = connection.prepareStatement(ADD_DEVELOPER_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, developer.getId());
            preparedStatement.setString(2, developer.getFirstName());
            preparedStatement.setString(3, developer.getLastName());
            preparedStatement.setLong(4, developer.getSpecialty().getId());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                developer.setId(generatedKeys.getLong(1));
            }

            var jdbcSkillRepository = new JdbcSkillRepositoryImpl(connection);
            developer.getSkills().forEach(jdbcSkillRepository::add);
            preparedStatement = connection.prepareStatement(ADD_DEVELOPER_SKILL_SQL);
            for (Skill skill : developer.getSkills()) {
                preparedStatement.setLong(1, developer.getId());
                preparedStatement.setLong(2, skill.getId());
                preparedStatement.executeUpdate();
            }
            return developer;

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            var preparedStatement = connection.prepareStatement(DELETE_DEVELOPER_SQL);
            preparedStatement.setLong(1, id);
            preparedStatement = connection.prepareStatement(DELETE_DEVELOPER_SKILL_SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return false;
    }

    @Override
    public boolean update(Long id, Developer developer) {
        return false;
    }

    @Override
    public Developer getById(Long id) {
        try {
            var preparedStatement = connection.prepareStatement(GET_DEVELOPER_SQL);
            preparedStatement.setLong(1, id);


        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return null;
    }

    @Override
    public Collection<Developer> getAll() {
        return null;
    }
}
