package com.db.mapper;

import com.entity.Session;
import com.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper {
    public Session mapSession(ResultSet resultSet) throws SQLException, JsonProcessingException {
        return Session.builder()
                .id(resultSet.getLong("sessionId"))
                .token(resultSet.getString("token"))
                .user(new ObjectMapper().readValue(resultSet.getString("userInSession"), User.class))
                .expireDate(resultSet.getLong("expireDate"))
                .build();
    }
}
