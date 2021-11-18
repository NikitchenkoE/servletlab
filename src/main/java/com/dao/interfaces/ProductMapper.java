package com.dao.interfaces;

import com.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProductMapper {

    public Product mapProduct(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong("productID"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .create(new Date(resultSet.getTimestamp("createDate").getTime()))
                .update(new Date(resultSet.getTimestamp("updateData").getTime()))
                .build();
    }
}
