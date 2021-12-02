package com.servlet;

import com.ServiceLocator;
import com.service.RegistrationService;
import com.service.util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private final RegistrationService registrationService = ServiceLocator.getDependency(RegistrationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(PageGenerator.init().getPage("registration.ftlh"));
        resp.setStatus(HttpServletResponse.SC_OK);
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
