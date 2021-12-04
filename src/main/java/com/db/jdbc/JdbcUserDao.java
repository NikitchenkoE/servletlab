package com.db.jdbc;

import com.db.interfaces.UserDao;
import com.db.mapper.UserMapper;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcUserDao implements UserDao {
    String SELECT_USER_BY_ID = "SELECT userID, username, role, password, sole FROM users WHERE userID=%s";
    String SELECT_USER_BY_NAME = "SELECT userID, username, role, password, sole FROM users WHERE username='%s'";
    String INSERT_INTO_TABLE = "INSERT INTO users(username, role, password, sole) VALUES (?,?,?,?)";
    String SELECT_ALL_FROM_USERS = "SELECT userID, username, role, password, sole FROM users";
    String UPDATE_BY_ID = "UPDATE users SET userID=?, username=?, role=?, password=?, sole=? WHERE userID=?";
    String DELETE_BY_ID = "DELETE FROM users WHERE userID=?";
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> get(long id) {
        log.info("get user by {}", id);
        return jdbcTemplate.query(String.format(SELECT_USER_BY_ID, id), resultSet -> {
            if (resultSet.next()) {
                return Optional.ofNullable(new UserMapper().mapUser(resultSet));
            } else return Optional.empty();
        });
    }

    @Override
    public List<User> getAll() {
        log.info("Get all users");

        return jdbcTemplate.query(SELECT_ALL_FROM_USERS, (resultSet, rowNum) -> new UserMapper().mapUser(resultSet));
    }

    @Override
    public void save(User user) {
        log.info("Saving user {}", user);
        jdbcTemplate.update(INSERT_INTO_TABLE, user.getUsername(),
                user.getRole().getName(),
                user.getSoledPassword(),
                user.getSole());
    }

    @Override
    public void update(User user) {
        log.info("Update user {}", user);
        jdbcTemplate.update(UPDATE_BY_ID, user.getId(),
                user.getUsername(),
                user.getRole().getName(),
                user.getSoledPassword(),
                user.getSole(),
                user.getId());
    }

    @Override
    public void delete(long id) {
        log.info("Delete user by id {}", id);
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("get user by {}", username);
        return jdbcTemplate.query(String.format(SELECT_USER_BY_NAME, username), resultSet -> {
            if (resultSet.next()) {
                return Optional.ofNullable(new UserMapper().mapUser(resultSet));
            } else return Optional.empty();
        });
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
