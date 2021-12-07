package com.web.filter;

import com.entity.User;
import com.entity.enums.Role;
import com.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

class SecurityFilterTest {
    SecurityFilter securityFilter = new SecurityFilter();

    @Test
    void testMainPagePathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testMainPagePath2ShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/products");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testMainProductsByIdShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/productsById");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testMainProductsByDescriptionShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/productsByDescription");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testLoginPagePathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/login");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testRegistrationPagePathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/registration");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testCartPagePathShouldReturn302AndRedirect() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/cart");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        String actualPath = httpServletResponse.getRedirectedUrl();
        assertEquals(302, actualCode);
        assertEquals("/login", actualPath);
    }

    @Test
    void testLogoutPathShouldReturn302AndRedirect() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/logout");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        String actualPath = httpServletResponse.getRedirectedUrl();
        assertEquals(302, actualCode);
        assertEquals("/login", actualPath);
    }

    @Test
    void testProductsAddPathShouldReturn302AndRedirect() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/products/add");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        String actualPath = httpServletResponse.getRedirectedUrl();
        assertEquals(302, actualCode);
        assertEquals("/login", actualPath);
    }

    @Test
    void testProductsUpdatePathShouldReturn302AndRedirect() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/products/update");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        String actualPath = httpServletResponse.getRedirectedUrl();
        assertEquals(302, actualCode);
        assertEquals("/login", actualPath);
    }

    @Test
    void testProductsDeletePathShouldReturn302AndRedirect() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/products/delete");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        String actualPath = httpServletResponse.getRedirectedUrl();
        assertEquals(302, actualCode);
        assertEquals("/login", actualPath);
    }

    @Test
    void testProductsDeleteFromCartPathShouldReturn302AndRedirect() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        when(mockReq.getRequestURI()).thenReturn("/cart/deleteFromCart");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        String actualPath = httpServletResponse.getRedirectedUrl();
        assertEquals(302, actualCode);
        assertEquals("/login", actualPath);
    }

    //    for user
    @Test
    void testCartPagePathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "user", Role.USER, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);


        when(mockReq.getRequestURI()).thenReturn("/cart");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testUserLogoutPathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "user", Role.USER, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/logout");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testUserProductsAddPathShouldReturn403() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "user", Role.USER, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/products/add");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(403, actualCode);
    }

    @Test
    void testUserProductsUpdatePathShouldReturn403() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "user", Role.USER, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/products/update");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(403, actualCode);
    }

    @Test
    void testUserProductsDeletePathShouldReturn403() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "user", Role.USER, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/products/delete");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(403, actualCode);
    }

    @Test
    void testUserProductsDeleteFromCartPathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "user", Role.USER, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/cart/deleteFromCart");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

//    for admin

    @Test
    void testAdminCartPagePathShouldReturn403() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "admin", Role.ADMIN, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);


        when(mockReq.getRequestURI()).thenReturn("/cart");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(403, actualCode);
    }

    @Test
    void testAminLogoutPathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "admin", Role.ADMIN, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/logout");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testAdminProductsAddPathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "admin", Role.ADMIN, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/products/add");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testAdminProductsUpdatePathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "admin", Role.ADMIN, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/products/update");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testAdminProductsDeletePathShouldReturn200() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "admin", Role.ADMIN, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/products/delete");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(200, actualCode);
    }

    @Test
    void testAdminProductsDeleteFromCartPathShouldReturn403() throws ServletException, IOException {
        SecurityService securityService = Mockito.mock(SecurityService.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        User user = new User(1L, "admin", Role.ADMIN, "password", "sole");
        Cookie cookie = new Cookie("cookie", "token");
        Cookie[] cookies = {cookie};
        when(mockReq.getCookies()).thenReturn(cookies);
        when(securityService.isLogged(cookies)).thenReturn(true);
        when(securityService.getAuthUser(cookies)).thenReturn(user);

        when(mockReq.getRequestURI()).thenReturn("/cart/deleteFromCart");
        when(mockReq.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("securityService")).thenReturn(securityService);

        securityFilter.doFilter(mockReq, httpServletResponse, mockFilterChain);
        int actualCode = httpServletResponse.getStatus();
        assertEquals(403, actualCode);
    }
}