package com.servlet;

import com.entity.Product;
import com.service.SecurityService;
import com.service.ProductService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;
    private Product productToBeUpdated;

    public UpdateProductServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            Map<String, Object> data = new HashMap<>();
            long productId = Long.parseLong(req.getParameter("idToUpdate"));
            productToBeUpdated = productService.get(productId).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
            data.put("productName", productToBeUpdated.getName());
            data.put("productPrice", productToBeUpdated.getPrice());
            data.put("productDescription", productToBeUpdated.getDescription());
            data.put("logged", String.valueOf(securityService.isLogged(req)));

            resp.getWriter().println(PageGenerator.init().getPage("updateProductPage.ftlh", data));
            resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("productName").isEmpty() || req.getParameter("productPrice").isEmpty() || req.getParameter("productDescription").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Product newProduct = productService.createProductAfterUpdate(req, productToBeUpdated);
            productService.update(newProduct);
            resp.sendRedirect("/products");
        }
    }
}
