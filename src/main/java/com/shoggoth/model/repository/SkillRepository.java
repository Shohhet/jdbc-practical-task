package com.shoggoth.model.repository;

import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.exceptions.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends GenericRepository<Long, Skill> {
    @Override
    Optional<Skill> add(Skill skill) throws RepositoryException;

    @Override
    Optional<Skill> getById(Long id) throws RepositoryException;

    @Override
    Optional<Skill> update(Skill skill) throws RepositoryException;

    @Override
    boolean delete(Long id) throws RepositoryException;

    @Override
    List<Skill> getAll() throws RepositoryException;

    boolean deleteForDevelopers(Long id) throws RepositoryException;
}

