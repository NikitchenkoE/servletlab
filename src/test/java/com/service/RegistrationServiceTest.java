package com.service;

import com.db.DataSourceFactory;
import com.db.jdbc.JdbcUserDao;
import com.db.SqlQueries;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationServiceTest {
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:testdb", "user", "user").load();
    JdbcUserDao jdbcUserDao = new JdbcUserDao();

    @BeforeEach
    void init(){
        jdbcUserDao.setDataSource(dataSourceFactory.getDataSource());
        flyway.migrate();
    }
    @AfterEach
    void dropTable() {
        try (Connection connection = dataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_USERS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        flyway.clean();
    }

    @Test
    void saveUserToDb() {
        RegistrationService registrationService = new RegistrationService();
        registrationService.setUserDao(jdbcUserDao);
        String name = "user";
        String password = "password";
        registrationService.saveUser(name, password);
        var userInDb = jdbcUserDao.get(1);
        assertEquals(name, userInDb.get().getUsername());
    }
}