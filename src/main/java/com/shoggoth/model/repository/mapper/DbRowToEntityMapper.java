package com.shoggoth.model.repository.mapper;

import com.shoggoth.model.exceptions.RepositoryException;

import java.sql.ResultSet;
import java.util.Optional;

public interface DbRowToEntityMapper<T> {
    Optional<T> map(ResultSet resultSet) throws RepositoryException;
}
