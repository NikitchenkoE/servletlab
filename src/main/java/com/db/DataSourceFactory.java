package com.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DataSourceFactory {
    String CREATE_PRODUCT_TABLE = "CREATE TABLE products (productID IDENTITY NOT NULL PRIMARY KEY, name varchar, price double precision, description varchar, createDate timestamp, updateDate timestamp)";
    String CREATE_USER_TABLE = "CREATE TABLE users (userID IDENTITY NOT NULL PRIMARY KEY, username varchar, password varchar, sole varchar)";
    String CREATE_COOKIE_TABLE = "CREATE TABLE cookies (cookiesID IDENTITY NOT NULL PRIMARY KEY, cookie varchar)";
    private String url = "jdbc:h2:mem:productsDb";
    private String user = "userPg";
    private String password = "userPg";

    public DataSourceFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        createTables();
    }

    public DataSourceFactory() {
        createTables();
    }

    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        return new HikariDataSource(config);
    }

    private void createTables() {
        log.info("product table created");
        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_PRODUCT_TABLE);
            statement.executeUpdate(CREATE_USER_TABLE);
            statement.executeUpdate(CREATE_COOKIE_TABLE);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
