package com.example.ottoservice.dao;

import com.example.ottoservice.model.Product;
import com.example.ottoservice.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class ProductDAOImpl implements EntityDAO<Product>{

    private static final String SQL_GET_ALL = "select * from product";
    private static final String SQL_GET_BY_CATEGORY = "select * from product where category_id = ?";
    private static final String SQL_GET_PRODUCT_BY_ID = "select * from product where product_id = ?";
    private static final String SQL_CREATE_PRODUCT = "insert into product(name, category_id, images, description) values (?, ?, ?, ?)";
    private static final String SQL_UPDATE_PRODUCT = "update product set name = ?, category_id = ?, images = ?, description = ? where product_id = ?";
    private static final String SQL_DLETE_PRODUCT = "delete from product where product_id = ?";

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product createOrUpdateEntity(Product entity) {
        KeyHolder keyHolder = null;
        if (entity.getProduct_id() > 0) {
            jdbcTemplate.update(SQL_UPDATE_PRODUCT, entity.getName(), entity.getCategoryId(), entity.getImages(),
                    entity.getDescription(), entity.getProduct_id());
        } else {
            // To insert data, you need to pre-compile the SQL and set up the data yourself.
            keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(conn -> {

                // Pre-compiling SQL
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS);

                // Set parameters
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getCategoryId());
                preparedStatement.setString(3, entity.getImages());
                preparedStatement.setString(4, entity.getDescription());

                return preparedStatement;

            }, keyHolder);

            entity.setProduct_id((long)keyHolder.getKeys().get("product_id"));
        }
        return entity;
    }

    @Override
    public Product getEntityById(Long id) {
        List<Product> products = jdbcTemplate.query(SQL_GET_PRODUCT_BY_ID, new ProductMapper(), id);
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    @Override
    public List<Product> getEntities() {
        return jdbcTemplate.query(SQL_GET_ALL, new ProductMapper());
    }

    public List<Product> getEntitiesByCategory(int id) {
        return jdbcTemplate.query(SQL_GET_BY_CATEGORY, new ProductMapper(), id);
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DLETE_PRODUCT, id) > 0;
    }
}
