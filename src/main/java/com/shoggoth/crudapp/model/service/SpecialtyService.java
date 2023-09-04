package com.shoggoth.crudapp.model.service;

import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    Optional<SpecialtyEntity> add(String name) throws ServiceException;
    Optional<SpecialtyEntity> get(String stringId) throws ServiceException;
    Optional<SpecialtyEntity> update(String stringId, String name) throws ServiceException;
    boolean delete(String stringId) throws ServiceException;
    List<SpecialtyEntity> getAll() throws ServiceException;

}
