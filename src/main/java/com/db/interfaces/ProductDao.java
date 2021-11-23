package com.db.interfaces;

import com.entity.Product;

import java.util.List;

public interface ProductDao extends Dao<Product> {

    List<Product> getByDescription(String description);
}
