package com.service;

import com.db.interfaces.SessionDao;
import com.db.interfaces.UserDao;
import com.entity.Session;
import com.entity.User;
import com.service.scheduleClean.Scheduler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SecurityService {
    private final SessionDao jdbcSessionDao;
    private final UserDao jdbcUserDao;
    private final int cookieExpirationDate;

    public SecurityService(SessionDao sessionDao, UserDao userDao, int cookieExpirationDate) {
        this.jdbcSessionDao = sessionDao;
        this.jdbcUserDao = userDao;
        this.cookieExpirationDate =cookieExpirationDate;

        //TODO fixme return to another place
        Scheduler cookieScheduler = new Scheduler(jdbcSessionDao, cookieExpirationDate * 1000L);
        cookieScheduler.startScheduling();
    }

    public boolean isAuth(String username, String password) {
        log.info("Checked data by username {} and password {}", username, password);
        boolean loginAllowed = false;
        Optional<User> userInDb = jdbcUserDao.findByUsername(username);
        if (userInDb.isPresent()) {
            String sole = userInDb.get().getSole();
            String soledPassword = DigestUtils.md5Hex(password + sole);
            if (soledPassword.equals(userInDb.get().getSoledPassword())) {
                loginAllowed = true;
            }
        }
        return loginAllowed;
    }

    public Cookie getNewToken(String username) {
        log.info("Created new cookie in db for user {}", username);
        String token = UUID.randomUUID().toString();
        User user = jdbcUserDao.findByUsername(username).orElseThrow(() -> new RuntimeException("Can't find user with this username"));
        jdbcSessionDao.save(Session.builder()
                .token(token)
                .user(user)
                .expireDate(new Date().getTime())
                .build());
        Cookie cookie = new Cookie("user-token", token);
        cookie.setMaxAge(cookieExpirationDate);
        return cookie;
    }

    public boolean isLogged(Cookie[] cookies) {
        boolean auth = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                auth = jdbcSessionDao.get(cookie.getValue()).isPresent();
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
                break;
            }
        }
        return readCookie;
    }

    public User getAuthUser(Cookie[] cookies) {
        User user = new User();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                var session = jdbcSessionDao.get(cookie.getValue()).orElseThrow(() -> new RuntimeException("Cant find session with this token"));
                user = session.getUser();
                break;
            }
        }
        return user;
    }
}
