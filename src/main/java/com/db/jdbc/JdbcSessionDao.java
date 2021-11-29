package com.db.jdbc;

import com.db.interfaces.SessionDao;
import com.entity.Session;
import com.db.mapper.SessionMapper;
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
public class JdbcSessionDao implements SessionDao {
    String INSERT_INTO_TABLE = "INSERT INTO sessions (token, userInSession, expireDate) VALUES (?,?,?)";
    String DELETE_BY_VALUE = "DELETE FROM sessions WHERE token=?";
    String SELECT_BY_TOKEN = "SELECT sessionId, token, userInSession, expireDate FROM sessions WHERE token=?";
    String SELECT_ALL_COOKIE = "SELECT sessionId, token, userInSession, expireDate FROM sessions";

    private final DataSource dataSource;
    SessionMapper cookieMapper = new SessionMapper();

    public JdbcSessionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Session session) {
        log.info("Saved {}", session);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TABLE)) {
            preparedStatement.setString(1, session.getToken());
            preparedStatement.setObject(2, session.getUser());
            preparedStatement.setLong(3, session.getExpireDate());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Exception when saved {}", session, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(String token) {
        log.info("Delete session with token {}", token);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_VALUE)) {
            preparedStatement.setString(1, token);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Exception when deleting {}", token, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Session> get(String token) {
        log.info("get session by {}", token);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_TOKEN)) {
                preparedStatement.setString(1, token);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(cookieMapper.mapCookie(resultSet));
                    } else return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            log.error("Error by getting cookie by id {}", token, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Session> getAllCookies() {
        List<Session> cookies = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COOKIE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                cookies.add(cookieMapper.mapCookie(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return cookies;
    }
}
