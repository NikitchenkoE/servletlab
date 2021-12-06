package com.db.mapper;

import com.entity.ProductInCart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartProductMapper implements RowMapper<ProductInCart> {
    @Override
    public ProductInCart mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ProductInCart.builder()
                .id(resultSet.getLong("cartItemId"))
                .userId(resultSet.getLong("userId"))
                .productId(resultSet.getLong("productId"))
                .build();
    }
}
