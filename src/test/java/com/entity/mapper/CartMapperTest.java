package com.entity.mapper;

import com.db.mapper.CartProductMapper;
import com.entity.ProductInCart;
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
    CartProductMapper cartMapper = new CartProductMapper();

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

        ProductInCart cart = new ProductInCart(1L,1L,1L);
        ProductInCart cartActual = cartMapper.mapProductInCart(resultSetMock);
        assertEquals(cart.toString(), cartActual.toString());
    }

    @Test
    void mapCartNotEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("cartItemId")).thenReturn(2L);
        Mockito.when(resultSetMock.getLong("userId")).thenReturn(2L);
        Mockito.when(resultSetMock.getLong("productId")).thenReturn(2L);

        ProductInCart cart = new ProductInCart(1L,1L,1L);
        ProductInCart cartActual = cartMapper.mapProductInCart(resultSetMock);
        assertNotEquals(cart.toString(), cartActual.toString());
    }
}