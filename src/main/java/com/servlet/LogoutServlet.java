package com.servlet;

import com.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private final LoginService loginService;

    public LogoutServlet(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie logoutCookie = loginService.logout(req);
        resp.addCookie(logoutCookie);
        resp.sendRedirect("/products");
    }
}
