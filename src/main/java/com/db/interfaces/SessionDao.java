package com.db.interfaces;

import com.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionDao {
    void save(Session cookie);

    void delete(String value);

    Optional<Session> get(String cookie);

    List<Session> getAllTokens();

    void cleanExpiredCookie(long presentTime);
}
