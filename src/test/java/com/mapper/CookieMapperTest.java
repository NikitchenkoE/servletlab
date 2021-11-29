package com.mapper;

import com.db.mapper.CookieMapper;
import com.entity.Session;
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

class CookieMapperTest {
    @Mock
    ResultSet resultSetMock;

    MockitoSession mockitoSession;
    CookieMapper cookieMapper = new CookieMapper();

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
        Mockito.when(resultSetMock.getLong("cookiesID")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("cookie")).thenReturn("cookie");
        Mockito.when(resultSetMock.getString("username")).thenReturn("username");
        Mockito.when(resultSetMock.getLong("expireDate")).thenReturn(1515284L);

        Session cookieEntity = new Session(1L,"cookie","username",1515284L);
        Session cookieActual = cookieMapper.mapCookie(resultSetMock);
        assertEquals(cookieEntity.toString(), cookieActual.toString());
    }

    @Test
    void mapCartNotEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("cookiesID")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("cookie")).thenReturn("cookie");
        Mockito.when(resultSetMock.getString("username")).thenReturn("username");
        Mockito.when(resultSetMock.getLong("expireDate")).thenReturn(25L);

        Session cookieEntity = new Session(2L,"cookie","username",1515284L);
        Session cookieActual = cookieMapper.mapCookie(resultSetMock);
        assertNotEquals(cookieEntity.toString(), cookieActual.toString());
    }

}