package com.shoggoth.crudapp.model.service;

import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    Optional<SkillEntity> add(String name) throws ServiceException;

    Optional<SkillEntity> get(String stringId) throws ServiceException;

    Optional<SkillEntity> update(String stringId, String name) throws ServiceException;

    boolean delete(String stringId) throws ServiceException;

    List<SkillEntity> getAll() throws ServiceException;


}
