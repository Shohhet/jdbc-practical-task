package com.shoggoth.repository;

import com.shoggoth.entity.Skill;
import com.shoggoth.exceptions.RepositoryException;

import java.util.Optional;

public interface SkillRepository extends GenericRepository<Long, Skill> {
    @Override
    Optional<Skill> add(Skill skill) throws RepositoryException;

    @Override
    boolean update(Skill skill) throws RepositoryException;

    @Override
    Optional<Skill> getById(Long id) throws RepositoryException;
}

