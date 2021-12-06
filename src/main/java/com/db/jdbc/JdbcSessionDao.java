package com.db.jdbc;

import com.db.interfaces.SessionDao;
import com.db.mapper.SessionMapper;
import com.entity.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcSessionDao implements SessionDao {
    private static final String INSERT_INTO_TABLE = "INSERT INTO sessions (token, userInSession, expireDate) VALUES (?,?,?)";
    private static final String DELETE_BY_TOKEN = "DELETE FROM sessions WHERE token=:token";
    private static final String DELETE_ALL_THAT_EXPIRED = "DELETE FROM sessions WHERE expireDate<?";
    private static final String SELECT_BY_TOKEN = "SELECT sessionId, token, userInSession, expireDate FROM sessions WHERE token=:token";
    private static final String SELECT_ALL_TOKENS = "SELECT sessionId, token, userInSession, expireDate FROM sessions";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(Session session) {
        log.info("Saved {}", session);
        try {
            jdbcTemplate.update(INSERT_INTO_TABLE, session.getToken(),
                    new ObjectMapper().writeValueAsString(session.getUser()),
                    session.getExpireDate());
        } catch (JsonProcessingException exception) {
            log.error("Exception when saved {}", session, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(String token) {
        log.info("Delete session with token {}", token);
        namedParameterJdbcTemplate.update(DELETE_BY_TOKEN, Collections.singletonMap("token", token));
    }

    @Override
    public void cleanExpiredCookie(long presentTime) {
        log.info("Cleaned session expired date of which < {}", presentTime);
        jdbcTemplate.update(DELETE_ALL_THAT_EXPIRED, presentTime);
    }

    @Override
    public Optional<Session> get(String token) {
        log.info("get session by {}", token);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_TOKEN, Collections.singletonMap("token", token), new SessionMapper()));
        }catch (EmptyResultDataAccessException exception) {
            log.info("No sessions found by id {}", token, exception);
            return Optional.empty();
        }
    }

    @Override
    public List<Session> getAllTokens() {
        return jdbcTemplate.query(SELECT_ALL_TOKENS, new SessionMapper());
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
