package com.db.interfaces;

import com.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<User>{
    Optional<User> findByUsername(String username);
}
