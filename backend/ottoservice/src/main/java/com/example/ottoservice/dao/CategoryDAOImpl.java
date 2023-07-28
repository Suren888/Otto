package com.example.ottoservice.dao;

import com.example.ottoservice.model.Category;
import com.example.ottoservice.model.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Component
public class CategoryDAOImpl implements EntityDAO<Category> {

    private static final String SQL_GET_ALL = "select * from category";
    private static final String SQL_GET_BY_ID = "select * from category where category_id = ?";
    private static final String SQL_CREATE_CATEGORY = "insert into category(name, parent_category_id) values (?, ?)";
    private static final String SQL_EDIT_CATEGORY = "update category set name = ?, parent_category_id = ? where category_id = ?";
    private static final String SQL_DELETE_CATEGORY = "delete from category where category_id = ?";
    private static final String SQL_DELETE_BUTCH_CATEGORY = "delete from category where category_id in (:ids)";


    JdbcTemplate jdbcTemplate;
    @Autowired
    public CategoryDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Category createOrUpdateEntity(Category entity) {
        int id;
        Object parentCategoryId = entity.getParentCategoryId() == 0 ? null : entity.getParentCategoryId();
        if (entity.getCategory_id() > 0) {
            id = jdbcTemplate.update(SQL_EDIT_CATEGORY, entity.getName(), parentCategoryId, entity.getCategory_id());
        } else {
            id = jdbcTemplate.update(SQL_CREATE_CATEGORY, entity.getName(), parentCategoryId);
        }

        entity.setCategory_id(id);
        return entity;
    }

    @Override
    public Category getEntityById(Long id) {
        List<Category> categories = jdbcTemplate.query(SQL_GET_BY_ID, new CategoryMapper(), id);
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(0);
    }

    @Override
    public List<Category> getEntities() {
        return jdbcTemplate.query(SQL_GET_ALL, new CategoryMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_CATEGORY, id) > 0;
    }

    public boolean deleteButch(Collection<Long> iDs) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", iDs);

        return jdbcTemplate.update(SQL_DELETE_BUTCH_CATEGORY,
                parameters) > 0;
    }

}
