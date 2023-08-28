package com.shoggoth.model.repository;

import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.exceptions.RepositoryException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends GenericRepository<Long, Developer> {
    @Override
    Optional<Developer> add(Developer developer) throws RepositoryException;

    @Override
    Optional<Developer> getById(Long id) throws RepositoryException;

    @Override
    Optional<Developer> update(Developer developer) throws RepositoryException;

    @Override
    boolean delete(Long id) throws RepositoryException;

    @Override
    List<Developer> getAll() throws RepositoryException;

    @Override
    void setConnection(Connection connection);

    boolean deleteSpecialtyForDevelopers(Long id) throws RepositoryException;

    void addDeveloperSkill(Long developerId, Long skillId) throws RepositoryException;
}
