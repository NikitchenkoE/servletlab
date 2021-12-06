package com.service;

import com.db.interfaces.SessionDao;
import com.db.interfaces.UserDao;
import com.entity.Session;
import com.entity.User;
import com.entity.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.Collectors;

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

    public void saveUser(String username, String password) {
        if (userDao.findByUsername(username).isEmpty()) {
            String sole = UUID.randomUUID().toString();
            String soledPassword = DigestUtils.md5Hex(password + sole);

            userDao.save(User.builder()
                    .username(username)
                    .role(Role.USER)
                    .soledPassword(soledPassword)
                    .sole(sole)
                    .build());
            log.info("{} successfully registered", username);
        } else {
            log.error("user with this nickname exist");
            throw new RuntimeException("User exist");
        }
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
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equalsIgnoreCase("user-token"))
                .collect(Collectors.toList())
                .stream()
                .anyMatch(cookie -> sessionDao.get(cookie.getValue()).isPresent());
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
        return Arrays.stream(cookies)
                .map(cookie -> sessionDao.get(cookie.getValue()).orElseThrow(() -> new RuntimeException("Cant find session with this token")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cant find session with this token"))
                .getUser();

    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
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
