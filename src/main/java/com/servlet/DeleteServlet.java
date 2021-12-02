package com.servlet;

import com.ServiceLocator;
import com.service.CartService;
import com.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private final ProductService productService = ServiceLocator.getDependency(ProductService.class);
    private final CartService cartService = ServiceLocator.getDependency(CartService.class);

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
