package com.servlet;

import com.service.CartService;
import com.service.SecurityService;
import com.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;
    private final CartService cartService;

    public DeleteServlet(ProductService productService, SecurityService securityService, CartService cartService) {
        this.productService = productService;
        this.securityService = securityService;
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var parameter = req.getParameter("idToDelete");
        if (parameter != null) {
            var productId = Long.parseLong(parameter);
            productService.delete(productId);
            cartService.deleteAllProductWithSameIdFromCart(productId);
            resp.sendRedirect("/products");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
