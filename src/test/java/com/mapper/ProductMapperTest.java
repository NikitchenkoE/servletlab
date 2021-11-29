package com.mapper;

import com.db.mapper.ProductMapper;
import com.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductMapperTest {
    @Mock
    ResultSet resultSetMock;

    MockitoSession mockitoSession;
    Date dateForTest = new Date();
    ProductMapper productMapper = new ProductMapper();

    @BeforeEach
    void before() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();
    }

    @AfterEach
    void after() {
        mockitoSession.finishMocking();
    }

    @Test
    void mapProductEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("productID")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("name")).thenReturn("test");
        Mockito.when(resultSetMock.getDouble("price")).thenReturn(100.00);
        Mockito.when(resultSetMock.getString("description")).thenReturn("description");
        Mockito.when(resultSetMock.getTimestamp("createDate")).thenReturn(new Timestamp(dateForTest.getTime()));
        Mockito.when(resultSetMock.getTimestamp("updateDate")).thenReturn(new Timestamp(dateForTest.getTime()));

        Product productExpected = new Product(1L, "test", 100.00, "description", dateForTest, dateForTest);
        Product productActual = productMapper.mapProduct(resultSetMock);
        assertEquals(productExpected.toString(), productActual.toString());
    }

    @Test
    void mapProductEqualsNotEquals() throws SQLException {
        Mockito.when(resultSetMock.getLong("productID")).thenReturn(1L);
        Mockito.when(resultSetMock.getString("name")).thenReturn("test");
        Mockito.when(resultSetMock.getDouble("price")).thenReturn(100.00);
        Mockito.when(resultSetMock.getTimestamp("createDate")).thenReturn(new Timestamp(dateForTest.getTime()));
        Mockito.when(resultSetMock.getTimestamp("updateDate")).thenReturn(new Timestamp(dateForTest.getTime()));

        Product productExpected = new Product(100L, "bedTest", 1000.00,"description", dateForTest, dateForTest);
        Product productActual = productMapper.mapProduct(resultSetMock);
        assertNotEquals(productExpected.toString(), productActual.toString());
    }
}