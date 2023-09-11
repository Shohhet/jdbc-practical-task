package com.shoggoth.crudapp.model.service;

import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    Optional<DeveloperEntity> add(String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException;

    Optional<DeveloperEntity> get(String stringId) throws ServiceException;

    Optional<DeveloperEntity> update(String stringId, String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException;

    boolean delete(String stringId) throws ServiceException;

    List<DeveloperEntity> getAll() throws ServiceException;



}
