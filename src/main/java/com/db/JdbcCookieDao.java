package com.db;

import com.db.interfaces.CookieDao;
import com.entity.CookieEntity;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class JdbcCookieDao implements CookieDao {
    String INSERT_INTO_TABLE = "INSERT INTO cookies (cookie) VALUES (?)";
    String DELETE_BY_ID = "DELETE FROM cookies WHERE cookiesID=?";
    String SELECT_BY_COOKIE = "SELECT cookiesID, cookie FROM cookies WHERE cookie=?";

    private final DataSource dataSource;

    public JdbcCookieDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(CookieEntity cookie) {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TABLE)){
        preparedStatement.setString(1, cookie.getCookie());
        preparedStatement.executeUpdate();
    } catch (SQLException throwables) {
        throw new RuntimeException(throwables);
    }
    }

    @Override
    public void delete(long id) {
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
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
                        return Optional.of(new CookieEntity(resultSet.getLong("cookiesID"), resultSet.getString("cookie")));
                    } else return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            log.error("Error by getting cookie by id {}", cookie, exception);
            throw new RuntimeException(exception);
        }
    }
}
