package com.example.ottoservice.dao;

import java.util.Collection;

public interface EntityDAO<T> {
    T createOrUpdateEntity(T entity);

    T getEntityById(Long id);
    Collection<T> getEntities();

    boolean delete(Long id);
}
