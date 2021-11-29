package com.mapper;

import com.db.mapper.UserMapper;
import com.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    @Mock
    ResultSet resultSetMock;

    MockitoSession mockitoSession;
    UserMapper userMapper = new UserMapper();

    @BeforeEach
    void before() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();
    }

    @AfterEach
    void after() {
        mockitoSession.finishMocking();
    }

    @Test
    void mapCartEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("userID")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("username")).thenReturn("username");
        Mockito.when(resultSetMock.getString("password")).thenReturn("password");
        Mockito.when(resultSetMock.getString("sole")).thenReturn("sole");

        User user = new User(1L,"username","password", "sole");
        User userActual = userMapper.mapUser(resultSetMock);
        assertEquals(user.toString(), userActual.toString());
    }
}