package com.ebanking.dao;

import java.util.List;

public interface BaseDAO<T> {
    T getById(Long id);
    List<T> getAll();
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(Long id);
}
