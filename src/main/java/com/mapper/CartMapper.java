package com.mapper;

import com.entity.Cart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper {
    public Cart mapCart(ResultSet resultSet) throws SQLException {
        return Cart.builder()
                .id(resultSet.getLong("cartItemId"))
                .userId(resultSet.getLong("userId"))
                .productId(resultSet.getLong("productId"))
                .build();
    }
}
