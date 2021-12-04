package com.filter;

import com.entity.mapper.MapToAuthorizedUserDto;
import com.service.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    private final String LOGIN_PATH = "/login";
    private final String REGISTRATION_PATH = "/registration";
    private final String MAIN_PAGE_PATH = "/";
    private final String MAIN_PAGE_PATH2 = "/products";
    private final List<String> allowedPagesWithoutAuth = Arrays.asList(LOGIN_PATH, REGISTRATION_PATH, MAIN_PAGE_PATH, MAIN_PAGE_PATH2);
    private final List<String> forbiddenPagesToAuthUser = Arrays.asList(LOGIN_PATH, REGISTRATION_PATH);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityService securityService = (SecurityService) servletRequest.getServletContext().getAttribute("securityService");
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var path = httpRequest.getRequestURI();

        boolean isAuth = securityService.isLogged(httpRequest.getCookies());

        if (!isAuth) {
            if (!allowedPagesWithoutAuth.contains(path)) {
                httpResponse.sendRedirect(LOGIN_PATH);
            }
        } else if (forbiddenPagesToAuthUser.contains(path)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.sendError(403);
        } else {
            MapToAuthorizedUserDto mapper = new MapToAuthorizedUserDto();
            servletRequest.setAttribute("user", mapper.mapToAuthorizedUserDto(securityService.getAuthUser(httpRequest.getCookies())));
        }

        servletRequest.setAttribute("isLogged", isAuth);
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }

}

