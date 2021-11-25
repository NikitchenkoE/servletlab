package com.servlet;

import com.service.CartService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteFromCartServlet extends HttpServlet {
    private final CartService cartService;

    public DeleteFromCartServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getParameter("idToDelete").isEmpty()) {
            cartService.deleteProductFromTheCart(Long.parseLong(req.getParameter("idToDelete")));
            resp.sendRedirect("/cart");
        }
    }
}
