package com.db.mapper;

import com.entity.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CookieMapper {
    public Session mapCookie(ResultSet resultSet) throws SQLException {
        return Session.builder()
                .id(resultSet.getLong("cookiesID"))
                .token(resultSet.getString("cookie"))
                .username(resultSet.getString("username"))
                .expireDate(resultSet.getLong("expireDate"))
                .build();
    }
}
