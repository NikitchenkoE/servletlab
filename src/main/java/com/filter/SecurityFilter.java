package com.filter;

import com.service.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public class SecurityFilter implements Filter {
    private final String LOGIN_PATH = "/login";
    private final String REGISTRATION_PATH = "/registration";
    private final String MAIN_PAGE_PATH = "/";
    private final String MAIN_PAGE_PATH2 = "/products";
    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var path = httpRequest.getServletPath();
        httpResponse.setContentType("text/html;charset=utf-8");

        if (!securityService.isLogged(httpRequest)) {
            if (!Objects.equals(path, LOGIN_PATH) && !Objects.equals(path, REGISTRATION_PATH) && !Objects.equals(path, MAIN_PAGE_PATH) && !Objects.equals(path, MAIN_PAGE_PATH2)) {
                httpResponse.sendRedirect(LOGIN_PATH);
            }
        } else if (Objects.equals(path, REGISTRATION_PATH)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}

