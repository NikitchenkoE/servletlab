package com.db.jdbc;

import com.db.interfaces.UserDao;
import com.db.mapper.UserMapper;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcUserDao implements UserDao {
    String SELECT_USER_BY_ID = "SELECT userID, username, role, password, sole FROM users WHERE userID=:id";
    String SELECT_USER_BY_NAME = "SELECT userID, username, role, password, sole FROM users WHERE username=:username";
    String INSERT_INTO_TABLE = "INSERT INTO users(username, role, password, sole) VALUES (?,?,?,?)";
    String SELECT_ALL_FROM_USERS = "SELECT userID, username, role, password, sole FROM users";
    String UPDATE_BY_ID = "UPDATE users SET userID=?, username=?, role=?, password=?, sole=? WHERE userID=?";
    String DELETE_BY_ID = "DELETE FROM users WHERE userID=?";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<User> get(long id) {
        log.info("get user by {}", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_ID, Collections.singletonMap("id", id), new UserMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.info("No users found by id {}", id, exception);
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        log.info("Get all users");
        return jdbcTemplate.query(SELECT_ALL_FROM_USERS, new UserMapper());
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
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_NAME, Collections.singletonMap("username", username), new UserMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.info("No users found by username {}", username, exception);
            return Optional.empty();
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
