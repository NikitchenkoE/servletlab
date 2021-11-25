package com.servlet;

import com.service.SecurityService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (securityService.isLogged(req)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            resp.getWriter().println(PageGenerator.init().getPage("login.html"));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("userName").isEmpty() || req.getParameter("password").isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            if (securityService.userDataCorrect(req.getParameter("userName"), req.getParameter("password"))) {
                Cookie cookie = securityService.getNewCookie();
                resp.addCookie(cookie);
                resp.sendRedirect("/products");
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.sendRedirect("/login");
            }
        }
    }
}
