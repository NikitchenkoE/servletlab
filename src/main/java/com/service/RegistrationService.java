package com.service;

import com.db.interfaces.UserDao;
import com.db.jdbc.JdbcUserDao;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.sql.DataSource;
import java.util.UUID;

@Slf4j
public class RegistrationService {
    private final UserDao jdbcUserDao;

    public RegistrationService(UserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public boolean saveUser(String username, String password) {
        if (jdbcUserDao.findByUsername(username).isEmpty()) {
            String sole = UUID.randomUUID().toString();
            String soledPassword = DigestUtils.md5Hex(password + sole);

            jdbcUserDao.save(User.builder()
                    .username(username)
                    .soledPassword(soledPassword)
                    .sole(sole)
                    .build());
            log.info("{} successfully registered", username);
            return true;
        } else {
            log.info("user with this nickname exist");
            return false;
        }
    }
}
