package com.entity.mapper;

import com.db.mapper.SessionMapper;
import com.entity.Session;
import com.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SessionMapperTest {
    User user1 = new User(1L,"user1","soledPassword1","sole1");
    User user2 = new User(2L,"user2","soledPassword2","sole2");
    @Mock
    ResultSet resultSetMock;

    MockitoSession mockitoSession;
    SessionMapper cookieMapper = new SessionMapper();

    @BeforeEach
    void before() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();
    }

    @AfterEach
    void after() {
        mockitoSession.finishMocking();
    }

    @Test
    void mapCartEquals() throws SQLException, JsonProcessingException {
        Mockito.when(resultSetMock.getLong("sessionId")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("token")).thenReturn("token");
        Mockito.when(resultSetMock.getString("userInSession")).thenReturn(new ObjectMapper().writeValueAsString(user1));
        Mockito.when(resultSetMock.getLong("expireDate")).thenReturn(1515284L);

        Session cookieEntity = new Session(1L,"token",user1,1515284L);
        Session cookieActual = cookieMapper.mapSession(resultSetMock);
        assertEquals(cookieEntity.toString(), cookieActual.toString());
    }

    @Test
    void mapCartNotEquals() throws SQLException, JsonProcessingException {
        Mockito.when(resultSetMock.getLong("sessionId")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("token")).thenReturn("cookie");
        Mockito.when(resultSetMock.getString("userInSession")).thenReturn(new ObjectMapper().writeValueAsString(user2));
        Mockito.when(resultSetMock.getLong("expireDate")).thenReturn(25L);

        Session session = new Session(2L,"cookie",user2,1515284L);
        Session actualSession = cookieMapper.mapSession(resultSetMock);
        assertNotEquals(session.toString(), actualSession.toString());
    }

}