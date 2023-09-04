package com.shoggoth.crudapp.model.repository;

import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends GenericRepository<Long, DeveloperEntity> {
    @Override
    Optional<DeveloperEntity> add(DeveloperEntity developerEntity) throws RepositoryException;

    @Override
    Optional<DeveloperEntity> getById(Long id) throws RepositoryException;

    @Override
    Optional<DeveloperEntity> update(DeveloperEntity developerEntity) throws RepositoryException;

    @Override
    boolean delete(Long id) throws RepositoryException;

    @Override
    List<DeveloperEntity> getAll() throws RepositoryException;

    @Override
    void setConnection(Connection connection);

    void deleteSpecialtyForDevelopers(Long id) throws RepositoryException;


    void addDeveloperSkill(Long developerId, Long skillId) throws RepositoryException;
}
