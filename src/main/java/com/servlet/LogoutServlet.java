package com.servlet;

import com.ServiceLocator;
import com.service.SecurityService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
