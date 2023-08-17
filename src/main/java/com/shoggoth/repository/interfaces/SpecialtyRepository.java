package com.shoggoth.repository.interfaces;

import com.shoggoth.pojo.Specialty;

import java.util.Collection;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long>{
    @Override
    Specialty add(Specialty specialty);

    @Override
    boolean delete(Long id);

    @Override
    boolean update(Long id, Specialty specialty);

    @Override
    Specialty getById(Long id);

    @Override
    Collection<Specialty> getAll();
}
