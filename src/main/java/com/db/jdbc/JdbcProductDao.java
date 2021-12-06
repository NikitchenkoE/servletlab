package com.db.jdbc;

import com.db.interfaces.ProductDao;
import com.db.mapper.ProductMapper;
import com.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcProductDao implements ProductDao {
    private static final String SELECT_BY_ID = "SELECT productID, name, price, description, createDate, updateDate FROM products WHERE productId=:id";
    private static final String SELECT_ALL = "SELECT productID, name, price, description, createDate, updateDate FROM products";
    private static final String INSERT_INTO_TABLE = "INSERT INTO products(name, price, description, createDate, updateDate) VALUES (?,?,?,?,?)";
    private static final String UPDATE_BY_ID = "UPDATE products SET productId=?, name=?, price=?, description=?, createDate=?, updateDate=? WHERE productId=?";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE productId=?";
    private static final String SELECT_BY_DESCRIPTION = "SELECT productID, name, price, description, createDate, updateDate FROM products WHERE description LIKE ?";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Optional<Product> get(long id) {
        log.info("get product by {}", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, Collections.singletonMap("id", id), new ProductMapper()));
        }catch (EmptyResultDataAccessException exception){
            log.info("No products found by id {}", id, exception);
            return Optional.empty();
        }
    }

    @Override
    public List<Product> getAll() {
        log.info("get all products");
        return jdbcTemplate.query(SELECT_ALL, new ProductMapper());
    }

    @Override
    public void save(Product productEntity) {
        log.info("Saving product {}", productEntity.toString());
        jdbcTemplate.update(INSERT_INTO_TABLE,
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getDescription(),
                new Timestamp(productEntity.getCreate().getTime()),
                new Timestamp(productEntity.getUpdate().getTime()));
    }

    @Override
    public void update(Product productEntity) {
        log.info("Updating product {}", productEntity.toString());
        jdbcTemplate.update(UPDATE_BY_ID,
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getDescription(),
                new Timestamp(productEntity.getCreate().getTime()),
                new Timestamp(productEntity.getUpdate().getTime()),
                productEntity.getId());
    }

    @Override
    public void delete(long id) {
        log.info("delete product by {}", id);
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public List<Product> getByDescription(String description) {
        log.info("Select by description {}", description);
        return jdbcTemplate.query(SELECT_BY_DESCRIPTION, new ProductMapper(), "%".concat(description).concat("%"));
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
