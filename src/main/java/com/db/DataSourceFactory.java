package com.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
public class DataSourceFactory {
    private final String url;
    private final String user;
    private final String password;

    public DataSourceFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DataSourceFactory(Properties properties) {
        this.url = properties.getProperty("db.Url");
        this.user = properties.getProperty("db.User");
        this.password = properties.getProperty("db.Password");
    }

    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        return new HikariDataSource(config);
    }
}
