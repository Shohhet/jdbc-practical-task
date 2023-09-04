package com.shoggoth.crudapp.model.repository.mapper.impl;

import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.repository.mapper.DbRowToEntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbRowToSkillMapper implements DbRowToEntityMapper<SkillEntity> {
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
    public Optional<SkillEntity> map(ResultSet resultSet) throws RepositoryException {
        Optional<SkillEntity> maybeSkill;
        try {
            SkillEntity skillEntity = new SkillEntity();
            skillEntity.setId(resultSet.getLong("id"));//TODO create constants for column names
            skillEntity.setName(resultSet.getString("name"));
            skillEntity.setStatus(Status.valueOf(resultSet.getString("status")));
            maybeSkill = Optional.of(skillEntity);
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeSkill;
    }
}
