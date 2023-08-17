package com.shoggoth.repository.interfaces;

import java.util.Collection;

public interface GenericRepository<T, ID> {
    T add(T t);
    boolean delete(ID id);
    boolean update(ID id, T t);
    T getById(ID id);
    Collection<T> getAll();

}
