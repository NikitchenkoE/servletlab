package com.db;

import com.db.jdbc.JdbcCookieDao;
import com.entity.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcCookieDaoTest {
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    JdbcCookieDao jdbcCookieDao = new JdbcCookieDao(dataSourceFactory.getDataSource());
    Session cookie1 = new Session(1L, "cookie1", "user1", 125);
    Session cookie2 = new Session(2L, "cookie2", "user2", 126);
    Session cookie3 = new Session(3L, "cookie3", "user3", 127);


    @BeforeEach
    void init() {
        jdbcCookieDao.save(cookie1);
        jdbcCookieDao.save(cookie2);
        jdbcCookieDao.save(cookie3);
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
    void delete() {
        assertNotNull(jdbcCookieDao.get("cookie1").orElse(null));
        assertNotNull(jdbcCookieDao.get("cookie2").orElse(null));
        assertNotNull(jdbcCookieDao.get("cookie3").orElse(null));
        jdbcCookieDao.delete("cookie1");
        jdbcCookieDao.delete("cookie2");
        jdbcCookieDao.delete("cookie3");
        assertNull(jdbcCookieDao.get("cookie1").orElse(null));
        assertNull(jdbcCookieDao.get("cookie2").orElse(null));
        assertNull(jdbcCookieDao.get("cookie3").orElse(null));
    }

    @Test
    void getAllCookieShouldReturnAllCookies() {
        List<Session> expectedList = new ArrayList<>();
        expectedList.add(cookie1);
        expectedList.add(cookie2);
        expectedList.add(cookie3);
        var cookies = jdbcCookieDao.getAllCookies();

        assertEquals(expectedList, cookies);
    }
}