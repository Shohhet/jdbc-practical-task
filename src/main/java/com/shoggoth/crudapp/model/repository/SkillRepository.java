package com.shoggoth.crudapp.model.repository;

import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends GenericRepository<Long, SkillEntity> {
    @Override
    Optional<SkillEntity> add(SkillEntity skillEntity) throws RepositoryException;

    @Override
    Optional<SkillEntity> getById(Long id) throws RepositoryException;

    @Override
    Optional<SkillEntity> update(SkillEntity skillEntity) throws RepositoryException;

    @Override
    boolean delete(Long id) throws RepositoryException;

    @Override
    List<SkillEntity> getAll() throws RepositoryException;

    void deleteForDevelopers(Long id) throws RepositoryException;

    Optional<SkillEntity> getByName(SkillEntity skillEntity) throws RepositoryException;
    List<SkillEntity> getDeveloperSkills(Long id) throws RepositoryException;
}

