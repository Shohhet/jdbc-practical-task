package com.shoggoth.repository;

import com.shoggoth.pojo.Developer;

import java.util.Collection;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Long add(Developer developer) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public boolean update(Long aLong, Developer developer) {
        return false;
    }

    @Override
    public Developer getById(Long aLong) {
        return null;
    }

    @Override
    public Collection<Developer> getAll() {
        return null;
    }
}
