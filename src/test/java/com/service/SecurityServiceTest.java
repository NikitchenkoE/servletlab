package com.service;

import com.db.DataSourceFactory;
import com.db.jdbc.JdbcSessionDao;
import com.db.SqlQueries;
import com.db.jdbc.JdbcUserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SecurityServiceTest {
    Properties properties = propertyLoader();
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    SecurityService securityService = new SecurityService(new JdbcSessionDao(dataSourceFactory.getDataSource()), new JdbcUserDao(dataSourceFactory.getDataSource()), Integer.parseInt(properties.getProperty("session.ExpirationDateInSeconds")));
    RegistrationService registrationService = new RegistrationService(new JdbcUserDao(dataSourceFactory.getDataSource()));

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
    }

    private static Properties propertyLoader() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fileInputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Problem when loading properties", exception);
        }
        return properties;
    }
}