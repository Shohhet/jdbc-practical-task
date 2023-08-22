package com.shoggoth.model.repository.mapper.impl;

import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.repository.mapper.DbRowToEntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbRowToSpecialtyMapper implements DbRowToEntityMapper<Specialty> {
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
    public Optional<Specialty> map(ResultSet resultSet) throws RepositoryException {
        Optional<Specialty> maybeSpecialty;
        try {
            Specialty specialty = new Specialty();
            specialty.setId(resultSet.getLong("id"));//TODO create constants for column names
            specialty.setName(resultSet.getString("name"));
            specialty.setStatus(Status.valueOf(resultSet.getString("status")));
            maybeSpecialty = Optional.of(specialty);
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeSpecialty;
    }
}
