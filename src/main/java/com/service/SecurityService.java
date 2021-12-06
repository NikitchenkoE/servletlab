package com.service;

import com.db.interfaces.SessionDao;
import com.db.interfaces.UserDao;
import com.entity.Session;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SecurityService {
    private SessionDao sessionDao;
    private UserDao userDao;
    private int cookieExpirationDate;

    public boolean isAuth(String username, String password) {
        log.info("Checked data by username {} and password {}", username, password);
        boolean loginAllowed = false;
        Optional<User> userInDb = userDao.findByUsername(username);
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
        User user = userDao.findByUsername(username).orElseThrow(() -> new RuntimeException("Can't find user with this username"));
        sessionDao.save(Session.builder()
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
                if (cookie.getName().equalsIgnoreCase("user-token")) {
                    auth = sessionDao.get(cookie.getValue()).isPresent();
                }
            }
        }
        return auth;
    }

    public Cookie logout(Cookie[] cookies) {
        Cookie readCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("user-token")) {
                cookie.setMaxAge(0);
                sessionDao.delete(cookie.getValue());
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
                var session = sessionDao.get(cookie.getValue()).orElseThrow(() -> new RuntimeException("Cant find session with this token"));
                user = session.getUser();
                break;
            }
        }
        return user;
    }

    @Scheduled(fixedDelay =  60 * 1000)
    public void schedule() {
        log.info("Delete old sessions");
        sessionDao.cleanExpiredCookie(new Date().getTime() - cookieExpirationDate * 1000L);
    }

    public void setSessionDao(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setCookieExpirationDate(int cookieExpirationDate) {
        this.cookieExpirationDate = cookieExpirationDate;
    }
}
