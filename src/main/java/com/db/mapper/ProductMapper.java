package com.db.mapper;

import com.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProductMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong("productID"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .description(resultSet.getString("description"))
                .create(new Date(resultSet.getTimestamp("createDate").getTime()))
                .update(new Date(resultSet.getTimestamp("updateDate").getTime()))
                .build();
    }
}
