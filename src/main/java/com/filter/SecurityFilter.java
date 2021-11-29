package com.filter;

import com.service.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SecurityFilter implements Filter {
    private final String LOGIN_PATH = "/login";
    private final String REGISTRATION_PATH = "/registration";
    private final String MAIN_PAGE_PATH = "/";
    private final String MAIN_PAGE_PATH2 = "/products";
    private final List<String> allowedPagesWithoutAuth = Arrays.asList(LOGIN_PATH, REGISTRATION_PATH, MAIN_PAGE_PATH, MAIN_PAGE_PATH2);
    private final List<String> forbiddenPagesToAuthUser = Arrays.asList(LOGIN_PATH, REGISTRATION_PATH);
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        securityService = (SecurityService) filterConfig.getServletContext().getAttribute("securityService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var path = httpRequest.getServletPath();
        boolean isAuth = securityService.isLogged(httpRequest.getCookies());
        httpResponse.setContentType("text/html;charset=utf-8");

        if (!isAuth) {
            if (!allowedPagesWithoutAuth.contains(path)) {
                httpResponse.sendRedirect(LOGIN_PATH);
            }
        } else if (forbiddenPagesToAuthUser.contains(path)) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendError(403);
            }
        else {
            servletRequest.setAttribute("user", securityService.getAuthUser(httpRequest.getCookies()));
        }

        servletRequest.setAttribute("isLogged", isAuth);
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }

}

