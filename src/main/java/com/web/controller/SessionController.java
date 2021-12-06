package com.web.controller;

import com.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SessionController {
    private final SecurityService securityService;

    @Autowired
    public SessionController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    protected String login(@RequestParam("userName") String username,
                           @RequestParam("password") String password,
                           HttpServletResponse response) {
        if (securityService.isAuth(username, password)) {
            Cookie cookie = securityService.getNewToken(username);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    protected String logout(HttpServletRequest req, HttpServletResponse resp) {
        Cookie logoutCookie = securityService.logout(req.getCookies());
        resp.addCookie(logoutCookie);
        return "redirect:/";
    }
}

