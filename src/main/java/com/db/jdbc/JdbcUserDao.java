package com.db.jdbc;

import com.db.interfaces.UserDao;
import com.entity.User;
import com.db.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcUserDao implements UserDao {
    String SELECT_USER_BY_ID = "SELECT userID, username, password, sole FROM users WHERE userID=?";
    String SELECT_USER_BY_NAME = "SELECT userID, username, password, sole FROM users WHERE username=?";
    String INSERT_INTO_TABLE = "INSERT INTO users(username, password, sole) VALUES (?,?,?)";
    String SELECT_ALL_FROM_USERS = "SELECT userID, username, password, sole FROM users";
    String UPDATE_BY_ID = "UPDATE users SET userID=?, username=?, password=?, sole=? WHERE userID=?";
    String DELETE_BY_ID = "DELETE FROM users WHERE userID=?";
    private final DataSource dataSource;
    UserMapper userMapper = new UserMapper();

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> get(long id) {
        log.info("get user by {}", id);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.ofNullable(userMapper.mapUser(resultSet));
                    } else return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            log.error("Error by getting product by id {}", id, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<User> getAll() {
        log.info("Get all users");
        List<User> userList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                userList.add(userMapper.mapUser(resultSet));
            }
        } catch (SQLException exception) {
            log.error("Exception when get all users", exception);
            throw new RuntimeException(exception);
        }
        return userList;
    }

    @Override
    public void save(User user) {
        log.info("Saving user {}", user);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TABLE)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getSoledPassword());
                preparedStatement.setString(3, user.getSole());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            log.error("Problem when saving {}", user, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void update(User user) {
        log.info("Update user {}", user);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getSoledPassword());
            preparedStatement.setString(4, user.getSole());
            preparedStatement.setLong(5, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Exception when update {}", user, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(long id) {
        log.info("Delete user by id {}", id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Exception when delete user by id {}", id, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("get user by {}", username);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.ofNullable(userMapper.mapUser(resultSet));
                    } else return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            log.error("Error by getting product by id {}", username, exception);
            throw new RuntimeException(exception);
        }
    }
}
