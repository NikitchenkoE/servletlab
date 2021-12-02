package com.service;

import com.db.interfaces.ProductDao;
import com.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public class ProductService {
    private ProductDao productDao;

    public void save(Product product) {
        productDao.save(createNewProduct(product));
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

    public void update(Product productToBeUpdated, Product product) {
        Date date = new Date();

        productDao.update(Product.builder()
                .id(productToBeUpdated.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build());
    }

    public List<Product> getByDescription(String description) {
        return productDao.getByDescription(description);
    }

    private Product createNewProduct(Product product) {
        Date date = new Date();
        return Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .create(date)
                .update(date)
                .build();
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
