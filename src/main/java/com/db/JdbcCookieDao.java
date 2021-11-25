package com.db;

import com.db.interfaces.CookieDao;
import com.entity.CookieEntity;
import com.mapper.CookieMapper;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcCookieDao implements CookieDao {
    String INSERT_INTO_TABLE = "INSERT INTO cookies (cookie, expireDate) VALUES (?,?)";
    String DELETE_BY_VALUE = "DELETE FROM cookies WHERE cookie=?";
    String SELECT_BY_COOKIE = "SELECT cookiesID, cookie, expireDate FROM cookies WHERE cookie=?";
    String SELECT_ALL_COOKIE = "SELECT cookiesID, cookie, expireDate FROM cookies";

    private final DataSource dataSource;
    CookieMapper cookieMapper = new CookieMapper();

    public JdbcCookieDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(CookieEntity cookie) {
        log.info("Saved {}", cookie);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TABLE)) {
            preparedStatement.setString(1, cookie.getCookie());
            preparedStatement.setLong(2,cookie.getExpireDate());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Exception when saved {}", cookie, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(String value) {
        log.info("Delete cookie with value {}", value);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_VALUE)) {
            preparedStatement.setString(1, value);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Exception when deleting {}", value, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<CookieEntity> get(String cookie) {
        log.info("get cookie by {}", cookie);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_COOKIE)) {
                preparedStatement.setString(1, cookie);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(cookieMapper.mapCookie(resultSet));
                    } else return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            log.error("Error by getting cookie by id {}", cookie, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<CookieEntity> getAllCookies(){
        List<CookieEntity> cookies = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COOKIE);
        ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                cookies.add(cookieMapper.mapCookie(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return cookies;
    }
}
