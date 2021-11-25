package com.servlet;

import com.service.SecurityService;
import com.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;

    public DeleteServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            var parameter = req.getParameter("idToDelete");
            if (parameter != null) {
                productService.delete(Long.parseLong(parameter));
                resp.sendRedirect("/products");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
    }
}
