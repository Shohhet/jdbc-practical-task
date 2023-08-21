package com.shoggoth.model.repository;

import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.RepositoryException;

import java.util.Optional;

public interface SpecialtyRepository extends GenericRepository<Long, Specialty> {
    @Override
    Optional<Specialty> add(Specialty specialty) throws RepositoryException;

    @Override
    Optional<Specialty> getById(Long id) throws RepositoryException;

    @Override
    Optional<Specialty> update(Specialty specialty) throws RepositoryException;

    @Override
    boolean delete(Long id) throws RepositoryException;
}
