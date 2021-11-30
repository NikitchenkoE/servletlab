package com.db.interfaces;

import com.entity.ProductInCart;

import java.util.List;

public interface CartDao {
    void delete(long id);

    void save(ProductInCart cart);

    List<ProductInCart> getCart(long userId);

    void deleteByProductId(long id);
}
