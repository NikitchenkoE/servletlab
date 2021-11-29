package com.db;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Slf4j
public class DataSourceFactory {
    private final String CREATE_PRODUCT_TABLE = "CREATE TABLE products (productID IDENTITY NOT NULL PRIMARY KEY, name varchar, price double precision, description varchar, createDate timestamp, updateDate timestamp)";
    private final String CREATE_USER_TABLE = "CREATE TABLE users (userID IDENTITY NOT NULL PRIMARY KEY, username varchar, password varchar, sole varchar)";
    private final String CREATE_COOKIE_TABLE = "CREATE TABLE cookies (cookiesID IDENTITY NOT NULL PRIMARY KEY, cookie varchar, username varchar, expireDate bigint)";
    private final String CREATE_CART_TABLE = "CREATE TABLE cart (cartItemId IDENTITY NOT NULL PRIMARY KEY, userId bigint, productId bigint)";
    private final String url;
    private final String user;
    private final String password;

    public DataSourceFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        createTables();
    }

    public DataSourceFactory(Properties properties) {
        this.url = properties.getProperty("db.Url");
        this.user = properties.getProperty("db.User");
        this.password = properties.getProperty("db.Password");
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
            statement.executeUpdate(CREATE_CART_TABLE);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
