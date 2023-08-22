package com.shoggoth.model.repository;

import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.exceptions.RepositoryException;

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

    boolean deleteSpecialtyForDevelopers(Long id) throws RepositoryException;

    boolean setSpecialtyId(Long developerId, Long specialtyId) throws RepositoryException;
    boolean addDeveloperSkill(Long developerId, Long skillId) throws RepositoryException;

}
