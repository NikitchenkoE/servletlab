package com.mapper;

import com.entity.Cart;
import com.entity.CookieEntity;
import jakarta.servlet.http.Cookie;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

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

        CookieEntity cookieEntity = new CookieEntity(1L,"cookie","username",1515284L);
        CookieEntity cookieActual = cookieMapper.mapCookie(resultSetMock);
        assertEquals(cookieEntity.toString(), cookieActual.toString());
    }

    @Test
    void mapCartNotEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("cookiesID")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("cookie")).thenReturn("cookie");
        Mockito.when(resultSetMock.getString("username")).thenReturn("username");
        Mockito.when(resultSetMock.getLong("expireDate")).thenReturn(25L);

        CookieEntity cookieEntity = new CookieEntity(2L,"cookie","username",1515284L);
        CookieEntity cookieActual = cookieMapper.mapCookie(resultSetMock);
        assertEquals(cookieEntity.toString(), cookieActual.toString());
    }

}