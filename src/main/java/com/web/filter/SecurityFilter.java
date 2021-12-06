package com.web.filter;

import com.entity.dto.AuthorizedUserDto;
import com.entity.enums.Role;
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
    private final List<String> allowedPagesWithoutAuth = Arrays.asList("/login", "/registration", "/", "/products", "/favicon.ico");
    private final List<String> allowedPagesToAdmin = Arrays.asList("/", "/products", "/products/add", "/products/update", "/logout", "/products/delete");
    private final List<String> allowedPagesToUser = Arrays.asList("/", "/products", "/cart", "/logout", "/cart/deleteFromCart");

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
                httpResponse.sendRedirect("/login");
            }
        } else {
            MapToAuthorizedUserDto mapper = new MapToAuthorizedUserDto();
            AuthorizedUserDto userDto = mapper.mapToAuthorizedUserDto(securityService.getAuthUser(httpRequest.getCookies()));

            if (userDto.getRole().equals(Role.USER) && allowedPagesToUser.contains(path)) {
                servletRequest.setAttribute("user", userDto);
            } else if (userDto.getRole().equals(Role.ADMIN) && allowedPagesToAdmin.contains(path)) {
                servletRequest.setAttribute("user", userDto);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendError(403);
            }
        }

        servletRequest.setAttribute("isLogged", isAuth);
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }

}

