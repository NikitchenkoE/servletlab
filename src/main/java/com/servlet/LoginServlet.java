package com.servlet;

import com.ServiceLocator;
import com.service.SecurityService;
import com.service.util.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final SecurityService securityService = ServiceLocator.getDependency(SecurityService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(PageGenerator.init().getPage("login.ftlh"));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("userName").isEmpty() || req.getParameter("password").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            if (securityService.isAuth(req.getParameter("userName"), req.getParameter("password"))) {
                Cookie cookie = securityService.getNewToken(req.getParameter("userName"));
                resp.addCookie(cookie);
                resp.sendRedirect("/products");
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.sendRedirect("/login");
            }
        }
    }
}
