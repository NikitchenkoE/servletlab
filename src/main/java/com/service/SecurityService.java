package com.service;

import com.db.DataSourceFactory;
import com.db.JdbcCookieDao;
import com.db.JdbcUserDao;
import com.entity.CookieEntity;
import com.entity.User;
import com.service.scheduleClean.CookieScheduler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Slf4j
public class SecurityService {
    private final JdbcCookieDao jdbcCookieDao;
    private final JdbcUserDao jdbcUserDao;
    private final int cookieExpirationDate;

    public SecurityService(DataSourceFactory dataSourceFactory, Properties properties) {
        this.jdbcCookieDao = new JdbcCookieDao(dataSourceFactory.getDataSource());
        this.jdbcUserDao = new JdbcUserDao(dataSourceFactory.getDataSource());
        this.cookieExpirationDate = Integer.parseInt(properties.getProperty("cookieExpirationDateInSeconds"));
        CookieScheduler cookieScheduler = new CookieScheduler(jdbcCookieDao, cookieExpirationDate * 1000L);
        cookieScheduler.startScheduling();
    }

    public boolean userDataCorrect(String username, String password) {
        log.info("Checked data by username {} and password {}", username, password);
        boolean loginAllowed = false;
        User userInDb = jdbcUserDao.findByUsername(username).orElse(null);
        if (userInDb != null) {
            String sole = userInDb.getSole();
            String soledPassword = DigestUtils.md5Hex(password + sole);
            if (soledPassword.equals(userInDb.getSoledPassword())) {
                loginAllowed = true;
            }
        }
        return loginAllowed;
    }

    public Cookie getNewCookie(String username) {
        log.info("Created new cookie in db for user {}", username);
        String value = UUID.randomUUID().toString();
        jdbcCookieDao.save(CookieEntity.builder()
                .cookie(value)
                .username(username)
                .expireDate(new Date().getTime())
                .build());
        Cookie cookie = new Cookie("user-token", value);
        cookie.setMaxAge(cookieExpirationDate);
        return new Cookie("user-token", value);
    }

    public boolean isLogged(HttpServletRequest req) {
        boolean auth = false;
        var cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                auth = jdbcCookieDao.get(cookie.getValue()).orElse(null) != null;
            }
        }
        return auth;
    }

    public Cookie logout(HttpServletRequest req) {
        Cookie readCookie = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("user-token")) {
                cookie.setMaxAge(0);
                readCookie = cookie;
            }
        }
        return readCookie;
    }
}
