package com.service;

import com.db.jdbc.JdbcUserDao;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.sql.DataSource;
import java.util.UUID;

@Slf4j
public class RegistrationService {
    private final JdbcUserDao jdbcUserDao;

    public RegistrationService(DataSource dataSource) {
        this.jdbcUserDao = new JdbcUserDao(dataSource);
    }

    public boolean saveUser(String username, String password) {
        if (!userExist(username)) {
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

    private boolean userExist(String username) {
        return jdbcUserDao.findByUsername(username).orElse(null) != null;
    }
}
