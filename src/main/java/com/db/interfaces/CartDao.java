package com.db.interfaces;

import com.entity.Cart;

import java.util.List;

public interface CartDao {
    void delete(long id);

    void save(Cart cart);

    List<Cart> getCart(String username);
}
