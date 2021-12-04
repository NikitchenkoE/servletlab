package com.db;

import com.db.jdbc.JdbcCartDao;
import com.entity.ProductInCart;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcCartDaoTest {
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:testdb", "user", "user").load();
    JdbcCartDao jdbcCartDao = new JdbcCartDao();
    ProductInCart productInCart = new ProductInCart(1L, 1L, 1L);
    ProductInCart productInCart2 = new ProductInCart(2L, 1L, 2L);
    ProductInCart productInCart3 = new ProductInCart(3L, 1L, 3L);


    @BeforeEach
    void init() {
        jdbcCartDao.setJdbcTemplate(new JdbcTemplate(dataSourceFactory.getDataSource()));
        flyway.migrate();
        jdbcCartDao.save(productInCart);
        jdbcCartDao.save(productInCart2);
        jdbcCartDao.save(productInCart3);
    }

    @AfterEach
    void dropTable() {
        try (Connection connection = dataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_USERS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_SESSIONS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_CART);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        flyway.clean();
    }

    @Test
    void testGetCartShouldReturnCartWithProducts() {
        var expected = new ArrayList<>();
        expected.add(productInCart);
        expected.add(productInCart2);
        expected.add(productInCart3);
        var actual = jdbcCartDao.getCart(1L);

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteProductItemInCartById() {
        var expected = new ArrayList<>();
        expected.add(productInCart2);
        expected.add(productInCart3);

        jdbcCartDao.delete(1L);
        var actual = jdbcCartDao.getCart(1L);

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteAllProductsWithSameIdFromCart() {
        ProductInCart productInCart = new ProductInCart(1L, 1L, 1L);
        jdbcCartDao.save(productInCart);

        var expected = new ArrayList<>();
        expected.add(productInCart2);
        expected.add(productInCart3);

        jdbcCartDao.deleteByProductId(1L);
        var actual = jdbcCartDao.getCart(1L);

        assertEquals(expected, actual);
    }


}