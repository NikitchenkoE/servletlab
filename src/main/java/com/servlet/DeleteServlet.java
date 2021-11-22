package com.servlet;

import com.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private final ProductService productService;

    public DeleteServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var parameter = req.getParameter("idToDelete");
        if (parameter != null) {
            productService.delete(Long.parseLong(parameter));
            resp.setContentType("text/html;charset=utf-8");
            resp.sendRedirect("/products");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
