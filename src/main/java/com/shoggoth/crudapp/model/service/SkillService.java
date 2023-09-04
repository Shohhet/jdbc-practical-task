package com.shoggoth.crudapp.model.service;

import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    Optional<SkillEntity> add(String name) throws ServiceException;

    Optional<SkillEntity> get(long id) throws ServiceException;

    Optional<SkillEntity> update(long id, String name) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    List<SkillEntity> getAll() throws ServiceException;


}
