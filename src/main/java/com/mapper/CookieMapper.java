package com.mapper;

import com.entity.CookieEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CookieMapper {
    public CookieEntity mapCookie(ResultSet resultSet) throws SQLException {
        return CookieEntity.builder()
                .id(resultSet.getLong("cookiesID"))
                .cookie(resultSet.getString("cookie"))
                .username(resultSet.getString("username"))
                .expireDate(resultSet.getLong("expireDate"))
                .build();
    }
}
