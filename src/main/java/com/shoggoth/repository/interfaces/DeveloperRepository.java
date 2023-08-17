package com.shoggoth.repository.interfaces;

import com.shoggoth.pojo.Developer;

import java.util.Collection;

public interface DeveloperRepository extends GenericRepository<Developer, Long>{
    @Override
    Developer add(Developer developer);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(Long id, Developer developer);

    @Override
    Developer getById(Long id);

    @Override
    Collection<Developer> getAll();
}
