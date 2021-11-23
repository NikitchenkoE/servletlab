package com.servlet;

import com.service.RegistrationService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
   private final RegistrationService registrationService;

    public RegistrationServlet(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            registrationService.saveUser(req.getParameter("userName"),req.getParameter("password"));
            resp.setContentType("text/html;charset=utf-8");
            resp.sendRedirect("/login");
        }
    }
}
