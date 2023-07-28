package com.example.ottoservice.service;

import com.example.ottoservice.dao.CategoryDAOImpl;
import com.example.ottoservice.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ServiceCategoryImpl implements ServiceEntity<Category> {

    @Autowired
    CategoryDAOImpl categoryDAO;

    @Override
    public List<Category> getAll() {
        return categoryDAO.getEntities();
    }

    @Override
    public Category getEntityById(long categoryId) {
        return categoryDAO.getEntityById(categoryId);
    }

    @Override
    public Category createEntity(Category entity) {
        return categoryDAO.createOrUpdateEntity(entity);
    }

    @Override
    public boolean deleteEntityByID(long id) {
        return categoryDAO.delete(id);
    }

    public boolean deleteEntities(Collection<Long> iDs) {
        return categoryDAO.deleteButch(iDs);
    }
}
