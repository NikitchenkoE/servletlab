package com.servlet;

import com.ServiceLocator;
import com.service.SecurityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private final SecurityService securityService = ServiceLocator.getDependency(SecurityService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie logoutCookie = securityService.logout(req.getCookies());
        resp.addCookie(logoutCookie);
        resp.sendRedirect("/products");
    }
}
