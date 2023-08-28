package com.shoggoth.model.service;

import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    Optional<Specialty> add(String name) throws ServiceException;
    Optional<Specialty> get(Long id) throws ServiceException;
    Optional<Specialty> update(Long id, String name) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
    List<Specialty> getAll() throws ServiceException;

}
