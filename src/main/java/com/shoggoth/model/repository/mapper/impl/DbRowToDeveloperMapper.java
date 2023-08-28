package com.shoggoth.model.repository.mapper.impl;

import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.repository.mapper.DbRowToEntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbRowToDeveloperMapper implements DbRowToEntityMapper<Developer> {
    private static DbRowToDeveloperMapper INSTANCE;

    public static DbRowToDeveloperMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbRowToDeveloperMapper();
        }
        return INSTANCE;
    }

    @Override
    public Optional<Developer> map(ResultSet resultSet) throws RepositoryException {
        Optional<Developer> maybeDeveloper;
        try {
            Specialty specialty = null;
            Developer developer = new Developer();
            developer.setId(resultSet.getLong("developer.id")); //TODO create constants for column names
            developer.setFirstName(resultSet.getString("developer.first_name"));
            developer.setLastName(resultSet.getString("developer.last_name"));
            String specialtyName = resultSet.getString("specialty.name");
            if (specialtyName != null) {
                specialty = new Specialty();
                specialty.setName(specialtyName);
                specialty.setId(resultSet.getLong("developer.specialty_id"));
                specialty.setStatus(Status.valueOf(resultSet.getString("specialty.status")));
            }
            developer.setSpecialty(specialty);
            developer.setStatus(Status.valueOf(resultSet.getString("developer.status")));
            maybeDeveloper = Optional.of(developer);
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeDeveloper;
    }
}
