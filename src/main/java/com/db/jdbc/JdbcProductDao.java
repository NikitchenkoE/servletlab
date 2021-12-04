package com.db.jdbc;

import com.db.interfaces.ProductDao;
import com.db.mapper.ProductMapper;
import com.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcProductDao implements ProductDao {
    String SELECT_BY_ID = "SELECT productID, name, price, description, createDate, updateDate FROM products WHERE productId=%s";
    String SELECT_ALL = "SELECT productID, name, price, description, createDate, updateDate FROM products";
    String INSERT_INTO_TABLE = "INSERT INTO products(name, price, description, createDate, updateDate) VALUES (?,?,?,?,?)";
    String UPDATE_BY_ID = "UPDATE products SET productId=?, name=?, price=?, description=?, createDate=?, updateDate=? WHERE productId=?";
    String DELETE_BY_ID = "DELETE FROM products WHERE productId=?";
    String SELECT_BY_DESCRIPTION = "SELECT productID, name, price, description, createDate, updateDate FROM products WHERE description LIKE '%s'";

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Product> get(long id) {
        log.info("get product by {}", id);
        return jdbcTemplate.query(String.format(SELECT_BY_ID, id), resultSet -> {
            if (resultSet.next()) {
                return Optional.ofNullable(new ProductMapper().mapProduct(resultSet));
            } else return Optional.empty();
        });
    }

    @Override
    public List<Product> getAll() {
        log.info("get all products");
        return jdbcTemplate.query(SELECT_ALL, (resultSet, rowNum) -> new ProductMapper().mapProduct(resultSet));
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
        return jdbcTemplate.query(String.format(SELECT_BY_DESCRIPTION, "%".concat(description).concat("%")), (resultSet, rowNum) -> new ProductMapper().mapProduct(resultSet));
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
