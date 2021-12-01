package com.service;

import com.db.interfaces.ProductDao;
import com.entity.Product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final com.db.interfaces.ProductDao productDao;

    public ProductService(ProductDao jdbcProductDao) {
        this.productDao = jdbcProductDao;
    }

    public void save(String productName, String productDescription, String productPrice) {
        productDao.save(createNewProduct(productName, productDescription, productPrice));
    }

    public void delete(long parseLong) {
        productDao.delete(parseLong);
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public Optional<Product> get(long productId) {
        return productDao.get(productId);
    }

    public void update(Product productToBeUpdated, String productName, String productDescription, String productPrice) {
        Date date = new Date();

        productDao.update(Product.builder()
                .id(productToBeUpdated.getId())
                .name(productName)
                .price(Double.parseDouble(productPrice))
                .description(productDescription)
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build());
    }

    public List<Product> getByDescription(String description) {
        return productDao.getByDescription(description);
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
