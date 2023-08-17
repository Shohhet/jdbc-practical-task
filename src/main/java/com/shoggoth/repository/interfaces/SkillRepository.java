package com.shoggoth.repository.interfaces;

import com.shoggoth.pojo.Skill;

import java.util.Collection;

public interface SkillRepository extends GenericRepository<Skill, Long> {
    @Override
    Skill add(Skill skill);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(Long id, Skill skill);

    @Override
    Skill getById(Long id);

    @Override
    Collection<Skill> getAll();
}

