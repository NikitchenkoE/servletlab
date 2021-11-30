package com.db.mapper;

import com.entity.ProductInCart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartProductMapper {
    public ProductInCart mapProductInCart(ResultSet resultSet) throws SQLException {
        return ProductInCart.builder()
                .id(resultSet.getLong("cartItemId"))
                .userId(resultSet.getLong("userId"))
                .productId(resultSet.getLong("productId"))
                .build();
    }
}
