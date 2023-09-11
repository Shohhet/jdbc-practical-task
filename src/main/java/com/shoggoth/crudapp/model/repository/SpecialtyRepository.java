package com.shoggoth.crudapp.model.repository;

import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface SpecialtyRepository extends GenericRepository<Long, SpecialtyEntity> {
    @Override
    Optional<SpecialtyEntity> add(SpecialtyEntity specialtyEntity) throws RepositoryException;

    @Override
    Optional<SpecialtyEntity> getById(Long id) throws RepositoryException;

    @Override
    Optional<SpecialtyEntity> update(SpecialtyEntity specialtyEntity) throws RepositoryException;

    @Override
    boolean delete(Long id) throws RepositoryException;

    @Override
    List<SpecialtyEntity> getAll() throws RepositoryException;

    @Override
    void setConnection(Connection connection);

    Optional<SpecialtyEntity> getByName(String name) throws RepositoryException;


}
