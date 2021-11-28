package com.servlet;

import com.service.CartService;
import com.service.SecurityService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends HttpServlet {
    private CartService cartService;
    private SecurityService securityService;

    @Override
    public void init() throws ServletException {
        cartService = (CartService) getServletContext().getAttribute("cartService");
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("products", cartService.findAllProductInCart(req));
        data.put("logged", String.valueOf(securityService.isLogged(req)));
        data.put("total", cartService.sumAllProducts(req));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.init().getPage("cart.ftlh", data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        cartService.addProductToCart(req);
        resp.sendRedirect("/products");
    }
}
