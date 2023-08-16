package com.shoggoth.repository;

import com.shoggoth.pojo.Developer;

import java.util.Collection;

public interface DeveloperRepository extends GenericRepository<Developer, Long>{
    @Override
    Long add(Developer developer);

    @Override
    boolean delete(Long aLong);

    @Override
    boolean update(Long aLong, Developer developer);

    @Override
    Developer getById(Long aLong);

    @Override
    Collection<Developer> getAll();
}
