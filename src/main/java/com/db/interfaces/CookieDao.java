package com.db.interfaces;

import com.entity.CookieEntity;

import java.util.Optional;

public interface CookieDao {
    void save(CookieEntity cookie);
    void delete(String value);
    Optional<CookieEntity> get(String cookie);
}
