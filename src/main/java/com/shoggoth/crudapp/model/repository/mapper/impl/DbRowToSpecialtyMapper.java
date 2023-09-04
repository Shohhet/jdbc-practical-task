package com.shoggoth.crudapp.model.repository.mapper.impl;

import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.repository.mapper.DbRowToEntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbRowToSpecialtyMapper implements DbRowToEntityMapper<SpecialtyEntity> {
    private static DbRowToSpecialtyMapper INSTANCE;

    private DbRowToSpecialtyMapper() {
    }

    public static DbRowToSpecialtyMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbRowToSpecialtyMapper();
        }
        return INSTANCE;

    }

    @Override
    public Optional<SpecialtyEntity> map(ResultSet resultSet) throws RepositoryException {
        Optional<SpecialtyEntity> maybeSpecialty;
        try {
            SpecialtyEntity specialtyEntity = new SpecialtyEntity();
            specialtyEntity.setId(resultSet.getLong("id"));//TODO create constants for column names
            specialtyEntity.setName(resultSet.getString("name"));
            specialtyEntity.setStatus(Status.valueOf(resultSet.getString("status")));
            maybeSpecialty = Optional.of(specialtyEntity);
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeSpecialty;
    }
}
