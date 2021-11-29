package com.db.interfaces;

import com.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);

    Optional<User> get(long id);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(long id);
}
