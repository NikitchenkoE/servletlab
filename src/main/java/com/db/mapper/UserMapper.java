package com.db.mapper;

import com.entity.User;
import com.entity.enums.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("userID"))
                .username(resultSet.getString("username"))
                .role(Role.valueOf(resultSet.getString("role")))
                .soledPassword(resultSet.getString("password"))
                .sole(resultSet.getString("sole"))
                .build();
    }
}
