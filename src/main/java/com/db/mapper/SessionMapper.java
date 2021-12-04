package com.db.mapper;

import com.entity.Session;
import com.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class SessionMapper {
    public Session mapSession(ResultSet resultSet){
        try {
            return Session.builder()
                    .id(resultSet.getLong("sessionId"))
                    .token(resultSet.getString("token"))
                    .user(new ObjectMapper().readValue(resultSet.getString("userInSession"), User.class))
                    .expireDate(resultSet.getLong("expireDate"))
                    .build();
        } catch (SQLException | JsonProcessingException exception) {
            log.error("Exception whe map session", exception);
            throw new RuntimeException(exception);
        }
    }
}
