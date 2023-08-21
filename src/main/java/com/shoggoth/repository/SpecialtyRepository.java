package com.shoggoth.repository;

import com.shoggoth.entity.Specialty;
import com.shoggoth.exceptions.RepositoryException;

import java.util.Optional;

public interface SpecialtyRepository extends GenericRepository<Long, Specialty> {
    @Override
    Optional<Specialty> add(Specialty specialty) throws RepositoryException;

    @Override
    boolean update(Specialty specialty) throws RepositoryException;

    @Override
    Optional<Specialty> getById(Long id) throws RepositoryException;

}
