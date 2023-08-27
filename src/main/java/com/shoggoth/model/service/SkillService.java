package com.shoggoth.model.service;

import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.exceptions.ServiceException;

import javax.sql.rowset.serial.SerialException;
import java.util.List;
import java.util.Optional;

public interface SkillService {
    Optional<Skill> add(String name) throws ServiceException;

    Optional<Skill> get(long id) throws ServiceException;

    Optional<Skill> update(long id, String name) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    List<Skill> getAll() throws ServiceException;


}
