package com.shoggoth.crudapp.model.repository;

import com.shoggoth.crudapp.model.entity.BaseEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<ID, T extends BaseEntity> {

    Optional<T> add(T t) throws RepositoryException;

    Optional<T> getById(ID id) throws RepositoryException;

    Optional<T> update(T t) throws RepositoryException;

    boolean delete(ID id) throws RepositoryException;

    List<T> getAll() throws RepositoryException;

    void setConnection(Connection connection);

}
