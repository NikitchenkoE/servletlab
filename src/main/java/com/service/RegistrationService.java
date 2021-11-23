package com.service;

import com.db.DataSourceFactory;
import com.db.JdbcUserDao;
import com.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class RegistrationService {
    private final JdbcUserDao jdbcUserDao;
    public RegistrationService(DataSourceFactory dataSourceFactory) {
        this.jdbcUserDao = new JdbcUserDao(dataSourceFactory.getDataSource());
    }

    public void saveUser(String username, String password){
        String sole = UUID.randomUUID().toString();
        String soledPassword = DigestUtils.md5Hex(password + sole);

        jdbcUserDao.save(User.builder()
                .username(username)
                .soledPassword(soledPassword)
                .sole(sole)
                .build());
    }
}
