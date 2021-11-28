package com.servlet;

import com.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteFromCartServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = (CartService) getServletContext().getAttribute("cartService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getParameter("idToDelete").isEmpty()) {
            cartService.deleteOneProductFromTheCart(Long.parseLong(req.getParameter("idToDelete")));
            resp.sendRedirect("/cart");
        }
    }
}
