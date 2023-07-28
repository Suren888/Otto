package com.example.ottoservice.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.ottoservice.model.Product;
import org.springframework.jdbc.core.RowMapper;

public class ProductMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet resultSet, int i) throws SQLException {

        Product product = new Product();
        product.setProduct_id(resultSet.getLong("product_id"));
        product.setName(resultSet.getString("name"));
        product.setCategoryId(resultSet.getInt("category_id"));
        product.setImages(resultSet.getString("images"));
        product.setDescription(resultSet.getString("description"));
        return product;
    }
}