package com.service;

import com.db.DataSourceFactory;
import com.db.JdbcCookieDao;
import com.db.JdbcUserDao;
import com.db.SqlQueries;
import com.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    LoginService loginService = new LoginService(dataSourceFactory);
    RegistrationService registrationService = new RegistrationService(dataSourceFactory);
    JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSourceFactory.getDataSource());
    JdbcCookieDao jdbcCookieDao = new JdbcCookieDao(dataSourceFactory.getDataSource());

    @AfterEach
    void dropTable() {
        try (Connection connection = dataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_USERS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_COOKIES);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void userDataCorrect() {
        registrationService.saveUser("user", "password");
        assertTrue(loginService.userDataCorrect("user", "password"));
        assertFalse(loginService.userDataCorrect("user", "passdasdawdadaword"));
        assertFalse(loginService.userDataCorrect("user112", "password"));
    }

    @Test
    void getNewCookie() {
        var cookie = loginService.getNewCookie();
        assertEquals(cookie.getValue(),jdbcCookieDao.get(cookie.getValue()).get().getCookie());
    }
}