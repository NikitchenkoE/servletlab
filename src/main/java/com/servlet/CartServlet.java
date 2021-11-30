package com.servlet;

import com.entity.User;
import com.service.CartService;
import com.service.util.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = (CartService) getServletContext().getAttribute("cartService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        User user = (User) req.getAttribute("user");
        if (user != null) {
            data.put("products", cartService.findAllProductInCart(user));
            data.put("total", cartService.sumAllProducts(user));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(PageGenerator.init().getPage("cart.ftlh", data));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getAttribute("user");
        if (user != null) {
            var productId = req.getParameter("productId");
            cartService.addProductToCart(user, productId);
        }
        resp.sendRedirect("/products");
    }
}
