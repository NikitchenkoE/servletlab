package com.service;

import com.db.jdbc.JdbcProductDao;
import com.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final JdbcProductDao jdbcProductDao;

    public ProductService(DataSource dataSource) {
        this.jdbcProductDao = new JdbcProductDao(dataSource);
    }

    public void save(String productName, String productDescription, String productPrice) {
        jdbcProductDao.save(createNewProduct(productName, productDescription, productPrice));
    }

    public void delete(long parseLong) {
        jdbcProductDao.delete(parseLong);
    }

    public List<Product> getAll() {
        return jdbcProductDao.getAll();
    }

    public Optional<Product> get(long productId) {
        return jdbcProductDao.get(productId);
    }

    public void update(Product productToBeUpdated, String productName, String productDescription, String productPrice) {
        Date date = new Date();

        jdbcProductDao.update(Product.builder()
                .id(productToBeUpdated.getId())
                .name(productName)
                .price(Double.parseDouble(productPrice))
                .description(productDescription)
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build());
    }

    public List<Product> getByDescription(String description) {
        return jdbcProductDao.getByDescription(description);
    }

    private Product createNewProduct(String productName, String productDescription, String productPrice) {
        Date date = new Date();
        return Product.builder()
                .name(productName)
                .price(Double.parseDouble(productPrice))
                .description(productDescription)
                .create(date)
                .update(date)
                .build();
    }
}
