package com.servlet;

import com.ServiceLocator;
import com.service.CartService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFromCartServlet extends HttpServlet {
    private final CartService cartService = ServiceLocator.getDependency(CartService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getParameter("idToDelete").isEmpty()) {
            cartService.deleteOneProductFromTheCart(Long.parseLong(req.getParameter("idToDelete")));
            resp.sendRedirect("/cart");
        }
    }
}
