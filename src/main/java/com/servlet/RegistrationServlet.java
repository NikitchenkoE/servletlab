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
        if (loginService.isLogged(req)){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        resp.getWriter().println(PageGenerator.init().getPage("registration.html"));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("userName").isEmpty() || req.getParameter("password").isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean isSaved;
            isSaved = registrationService.saveUser(req.getParameter("userName"), req.getParameter("password"));
            if (!isSaved) {
                resp.setContentType("text/html;charset=utf-8");
                resp.sendRedirect("/registration");
            } else {
                resp.setContentType("text/html;charset=utf-8");
                resp.sendRedirect("/login");
            }
        }
    }
}
