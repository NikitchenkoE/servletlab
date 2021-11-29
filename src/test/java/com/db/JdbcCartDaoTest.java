package com.db;

import com.db.jdbc.JdbcCartDao;
import com.entity.Cart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcCartDaoTest {
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    JdbcCartDao jdbcCartDao = new JdbcCartDao(dataSourceFactory.getDataSource());
    Cart cart1 = new Cart(1L, 1L, 1L);
    Cart cart2 = new Cart(2L, 1L, 2L);
    Cart cart3 = new Cart(3L, 1L, 3L);




    @BeforeEach
    void init() {
        jdbcCartDao.save(cart1);
        jdbcCartDao.save(cart2);
        jdbcCartDao.save(cart3);
    }

    @AfterEach
    void dropTable() {
        try (Connection connection = dataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_USERS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_COOKIES);
            statement.executeUpdate(SqlQueries.DROP_TABLE_CART);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getCart() {
        var expected = new ArrayList<>();
        expected.add(cart1);
        expected.add(cart2);
        expected.add(cart3);
        var actual = jdbcCartDao.getCart(1L);

        assertEquals(expected, actual);
    }
}