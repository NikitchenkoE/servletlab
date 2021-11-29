package com.db.interfaces;

import com.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> getByDescription(String description);

    Optional<Product> get(long id);

    List<Product> getAll();

    void save(Product productEntity);

    void update(Product productEntity);

    void delete(long id);
}
