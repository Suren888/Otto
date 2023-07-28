package com.example.ottoservice.service;


import java.util.List;

public interface ServiceEntity<T> {

    List<T> getAll();

    T createEntity(T entity);

    T getEntityById(long id);

    boolean deleteEntityByID(long id);

}
