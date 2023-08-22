package com.shoggoth.model.service;

import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    Optional<Developer> add(Developer developer, List<Skill> skills, Specialty specialty) throws ServiceException;
    Optional<Developer> get(Long id) throws ServiceException;
    Optional<Developer> update(Developer developer, List<Skill> skills, Specialty specialty) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
    List<Developer> getAll() throws ServiceException;


}
