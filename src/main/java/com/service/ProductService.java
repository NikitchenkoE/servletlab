package com.service;

import com.db.JdbcProductDao;
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

    public void save(HttpServletRequest req) {
        jdbcProductDao.save(createNewProduct(req));
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

    public void update(Product newProduct) {
        jdbcProductDao.update(newProduct);
    }

    public List<Product> getByDescription(String description) {
        return jdbcProductDao.getByDescription(description);
    }

    public Product createProductAfterUpdate(HttpServletRequest req, Product productToBeUpdated) {
        Date date = new Date();
        return Product.builder()
                .id(productToBeUpdated.getId())
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .description(req.getParameter("productDescription"))
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build();
    }

    private Product createNewProduct(HttpServletRequest req) {
        Date date = new Date();
        return Product.builder()
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .description(req.getParameter("productDescription"))
                .create(date)
                .update(date)
                .build();
    }
}
