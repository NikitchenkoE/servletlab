package com.dao.interfaces;

import com.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDaoInterface extends Dao<Product>{
    @Override
    Optional<Product> get(long id);

    @Override
    List<Product> getAll();

    @Override
    void save(Product product);

    @Override
    void update(Product product);

    @Override
    void delete(long id);
}
