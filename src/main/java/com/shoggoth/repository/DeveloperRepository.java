package com.shoggoth.repository;

import com.shoggoth.entity.Developer;
import com.shoggoth.exceptions.RepositoryException;

import java.util.Optional;

public interface DeveloperRepository extends GenericRepository<Long, Developer> {
    @Override
    Optional<Developer> add(Developer developer) throws RepositoryException;


    @Override
    boolean update(Developer developer) throws RepositoryException;

    @Override
    Optional<Developer> getById(Long id) throws RepositoryException;
}
