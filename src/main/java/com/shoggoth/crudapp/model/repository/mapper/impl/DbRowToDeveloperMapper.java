package com.shoggoth.crudapp.model.repository.mapper.impl;

import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.repository.mapper.DbRowToEntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbRowToDeveloperMapper implements DbRowToEntityMapper<DeveloperEntity> {
    private static DbRowToDeveloperMapper INSTANCE;

    public static DbRowToDeveloperMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbRowToDeveloperMapper();
        }
        return INSTANCE;
    }

    @Override
    public Optional<DeveloperEntity> map(ResultSet resultSet) throws RepositoryException {
        Optional<DeveloperEntity> maybeDeveloper;
        try {
            SpecialtyEntity specialtyEntity = null;
            DeveloperEntity developerEntity = new DeveloperEntity();
            developerEntity.setId(resultSet.getLong("developer.id")); //TODO create constants for column names
            developerEntity.setFirstName(resultSet.getString("developer.first_name"));
            developerEntity.setLastName(resultSet.getString("developer.last_name"));
            String specialtyName = resultSet.getString("specialty.name");
            if (specialtyName != null) {
                specialtyEntity = new SpecialtyEntity();
                specialtyEntity.setName(specialtyName);
                specialtyEntity.setId(resultSet.getLong("developer.specialty_id"));
                specialtyEntity.setStatus(Status.valueOf(resultSet.getString("specialty.status")));
            }
            developerEntity.setSpecialty(specialtyEntity);
            developerEntity.setStatus(Status.valueOf(resultSet.getString("developer.status")));
            maybeDeveloper = Optional.of(developerEntity);
        } catch (SQLException e) {
            throw new RepositoryException(e);//TODO Add logger
        }
        return maybeDeveloper;
    }
}
