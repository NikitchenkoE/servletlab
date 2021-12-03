package com.servlet;

import com.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends HttpServlet {
    private final SecurityService securityService;

    public LoginController(SecurityService securityService) {
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
}

