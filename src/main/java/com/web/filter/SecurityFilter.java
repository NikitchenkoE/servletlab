package com.web.filter;

import com.entity.dto.AuthorizedUserDto;
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
    private final String LOGOUT_PATH = "/logout";
    private final String ADD_PRODUCT_PAGE = "/products/add";
    private final String DELETE_PRODUCT_FROM_MAIN_PAGE = "/products/delete";
    private final String DELETE_PRODUCT_FROM_CART = "/cart/deleteFromCart";
    private final String UPDATE_PRODUCT_PAGE = "/products/update";
    private final String CART_PAGE = "/cart";
    private final String FAVICON = "/favicon.ico";
    private final List<String> allowedPagesWithoutAuth = Arrays.asList(LOGIN_PATH, REGISTRATION_PATH, MAIN_PAGE_PATH, MAIN_PAGE_PATH2, FAVICON);
    private final List<String> forbiddenPagesToAuthUser = Arrays.asList(LOGIN_PATH, REGISTRATION_PATH);
    private final List<String> allowedPagesToAdmin = Arrays.asList(MAIN_PAGE_PATH, MAIN_PAGE_PATH2, ADD_PRODUCT_PAGE, UPDATE_PRODUCT_PAGE, LOGOUT_PATH, DELETE_PRODUCT_FROM_MAIN_PAGE);
    private final List<String> allowedPagesToUser = Arrays.asList(MAIN_PAGE_PATH, MAIN_PAGE_PATH2, CART_PAGE, LOGOUT_PATH, DELETE_PRODUCT_FROM_CART);

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
            AuthorizedUserDto userDto = mapper.mapToAuthorizedUserDto(securityService.getAuthUser(httpRequest.getCookies()));
            if (userDto.getRole().equals("USER") && !allowedPagesToUser.contains(path) ||
                    userDto.getRole().equals("ADMIN") && !allowedPagesToAdmin.contains(path)) {

                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendError(403);
            } else {
                servletRequest.setAttribute("user", userDto);
            }
        }

        servletRequest.setAttribute("isLogged", isAuth);
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }

}

