package com.mapper;

import com.db.mapper.CartMapper;
import com.entity.Cart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CartMapperTest {
    @Mock
    ResultSet resultSetMock;

    MockitoSession mockitoSession;
    CartMapper cartMapper = new CartMapper();

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
        Mockito.when(resultSetMock.getLong("cartItemId")).thenReturn(1L);
        Mockito.when(resultSetMock.getLong("userId")).thenReturn(1L);
        Mockito.when(resultSetMock.getLong("productId")).thenReturn(1L);

        Cart cart = new Cart(1L,1L,1L);
        Cart cartActual = cartMapper.mapCart(resultSetMock);
        assertEquals(cart.toString(), cartActual.toString());
    }

    @Test
    void mapCartNotEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("cartItemId")).thenReturn(2L);
        Mockito.when(resultSetMock.getLong("userId")).thenReturn(2L);
        Mockito.when(resultSetMock.getLong("productId")).thenReturn(2L);

        Cart cart = new Cart(1L,1L,1L);
        Cart cartActual = cartMapper.mapCart(resultSetMock);
        assertNotEquals(cart.toString(), cartActual.toString());
    }
}