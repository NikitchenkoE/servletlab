package com.service;

import com.db.DataSourceFactory;
import com.db.JdbcCookieDao;
import com.db.JdbcUserDao;
import com.db.SqlQueries;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    Properties properties = propertyLoader();
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    SecurityService securityService = new SecurityService(dataSourceFactory,properties);
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
            statement.executeUpdate(SqlQueries.DROP_TABLE_CART);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void userDataCorrect() {
        registrationService.saveUser("user", "password");
        assertTrue(securityService.userDataCorrect("user", "password"));
        assertFalse(securityService.userDataCorrect("user", "passdasdawdadaword"));
        assertFalse(securityService.userDataCorrect("user112", "password"));
    }

    @Test
    void getNewCookie() {
        var cookie = securityService.getNewCookie("user");
        assertEquals(cookie.getValue(),jdbcCookieDao.get(cookie.getValue()).get().getCookie());;
        assertEquals(jdbcCookieDao.get(cookie.getValue()).get().getUsername(),"user");
    }

    private static Properties propertyLoader(){
        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/projectProp.properties")){
            properties.load(fileInputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Problem when loading properties", exception);
        }
        return properties;
    }
}