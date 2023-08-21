package com.shoggoth.repository;

import com.shoggoth.exceptions.RepositoryException;

import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<ID, T> {
    Optional<T> add(T t) throws RepositoryException;

    boolean delete(ID id) throws RepositoryException;

    boolean update(T t) throws RepositoryException;

    Optional<T> getById(ID id) throws RepositoryException;


}
