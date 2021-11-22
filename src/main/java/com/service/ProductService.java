package com.service;

import com.dao.JdbcProductDao;
import com.db.DataSourceFactory;
import com.entity.Product;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final JdbcProductDao jdbcProductDao;

    public ProductService(DataSourceFactory dataSourceFactory) {
        this.jdbcProductDao = new JdbcProductDao(dataSourceFactory.getDataSource());
    }

    public void save(Product newProduct) {
        jdbcProductDao.save(newProduct);
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
}
