package com.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataConnectionPull {
    private String url = "jdbc:h2:mem:productsDb";
    private String user = "userPg";
    private String password = "userPg";

    public DataConnectionPull(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DataConnectionPull() {
    }

    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        return new HikariDataSource(config);
    }
}
