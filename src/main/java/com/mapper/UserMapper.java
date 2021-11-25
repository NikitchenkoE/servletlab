package com.mapper;

import com.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User mapUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("userID"))
                .username(resultSet.getString("username"))
                .soledPassword(resultSet.getString("password"))
                .sole(resultSet.getString("sole"))
                .build();
    }
}
