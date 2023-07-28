package com.example.ottoservice.service;

import com.example.ottoservice.dao.ProductDAOImpl;
import com.example.ottoservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProductImpl implements ServiceEntity<Product>{

    @Autowired
    ProductDAOImpl productDAO;

    @Override
    public List<Product> getAll() {
        return productDAO.getEntities();
    }

    public List<Product> getEntityByCategory(int categoryId) {
        return productDAO.getEntitiesByCategory(categoryId);
    }

    @Override
    public Product createEntity(Product product) {
        return productDAO.createOrUpdateEntity(product);
    }

    @Override
    public Product getEntityById(long id) {
        return productDAO.getEntityById(id);
    }

    @Override
    public boolean deleteEntityByID(long id) {
        return productDAO.delete(id);
    }
}
