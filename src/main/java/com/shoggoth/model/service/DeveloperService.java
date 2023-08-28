package com.shoggoth.model.service;

import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    Optional<Developer> add(String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException;

    Optional<Developer> get(Long id) throws ServiceException;

    Optional<Developer> update(Long id, String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    List<Developer> getAll() throws ServiceException;


}
