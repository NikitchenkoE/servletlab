package com.servlet;

import com.ServiceLocator;
import com.entity.Product;
import com.service.ProductService;
import com.service.util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    private final ProductService productService = ServiceLocator.getDependency(ProductService.class);
    private Product productToBeUpdated;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        long productId = Long.parseLong(req.getParameter("idToUpdate"));
        productToBeUpdated = productService.get(productId).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        data.put("productName", productToBeUpdated.getName());
        data.put("productPrice", productToBeUpdated.getPrice());
        data.put("productDescription", productToBeUpdated.getDescription());

        resp.getWriter().println(PageGenerator.init().getPage("updateProductPage.ftlh", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("productName").isEmpty() || req.getParameter("productPrice").isEmpty() || req.getParameter("productDescription").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            productService.update(productToBeUpdated, req.getParameter("productName"), req.getParameter("productDescription"), req.getParameter("productPrice"));
            resp.sendRedirect("/products");
        }
    }
}
