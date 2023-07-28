package com.example.ottoservice.model.mapper;

import java.sql.ResultSet;
        import java.sql.SQLException;

import com.example.ottoservice.model.Category;
import org.springframework.jdbc.core.RowMapper;

public class CategoryMapper implements RowMapper<Category> {

    public Category mapRow(ResultSet resultSet, int i) throws SQLException {

        Category category = new Category();
        category.setCategory_id(resultSet.getInt("category_id"));
        category.setName(resultSet.getString("name"));
        category.setParentCategoryId(resultSet.getInt("parent_category_id")); ;
        return category;
    }
}