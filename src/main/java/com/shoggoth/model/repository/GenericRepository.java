package com.shoggoth.model.repository;

import com.shoggoth.model.entity.Entity;
import com.shoggoth.model.exceptions.RepositoryException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<ID, T extends Entity> {

    Optional<T> add(T t) throws RepositoryException;

    Optional<T> getById(ID id) throws RepositoryException;

    Optional<T> update(T t) throws RepositoryException;

    boolean delete(ID id) throws RepositoryException;

    List<T> getAll() throws RepositoryException;

    void setConnection(Connection connection);

}
