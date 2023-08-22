package com.shoggoth.model.repository.mapper.impl;

import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.repository.mapper.DbRowToEntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbRowToSkillMapper implements DbRowToEntityMapper<Skill> {
    private static DbRowToSkillMapper INSTANCE;

    private DbRowToSkillMapper() {
    }
    public static DbRowToSkillMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbRowToSkillMapper();
        }
        return INSTANCE;
    }

    @Override
    public Optional<Skill> map(ResultSet resultSet) throws RepositoryException {
        Optional<Skill> maybeSkill;
        try {
            Skill skill = new Skill();
            skill.setId(resultSet.getLong("id"));//TODO create constants for column names
            skill.setName(resultSet.getString("name"));
            skill.setStatus(Status.valueOf(resultSet.getString("status")));
            maybeSkill = Optional.of(skill);
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeSkill;
    }
}
