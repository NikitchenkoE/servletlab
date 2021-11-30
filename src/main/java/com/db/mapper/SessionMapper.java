package com.db.mapper;

import com.entity.Session;
import com.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper {
    public Session mapSession(ResultSet resultSet) throws SQLException {
        return Session.builder()
                .id(resultSet.getLong("sessionId"))
                .token(resultSet.getString("token"))
                .user((User) resultSet.getObject("userInSession"))
                .expireDate(resultSet.getLong("expireDate"))
                .build();
    }
}
