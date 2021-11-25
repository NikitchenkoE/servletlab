package com.servlet;

import com.service.LoginService;
import com.service.RegistrationService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private final RegistrationService registrationService;
    private final LoginService loginService;

    public RegistrationServlet(RegistrationService registrationService, LoginService loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!loginService.isLogged(req)) {
            resp.getWriter().println(PageGenerator.init().getPage("registration.html"));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("userName").isEmpty() || req.getParameter("password").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean isSaved = registrationService.saveUser(req.getParameter("userName"), req.getParameter("password"));
            if (!isSaved) {
                resp.sendRedirect("/registration");
            } else {
                resp.sendRedirect("/login");
            }
        }
    }
}
