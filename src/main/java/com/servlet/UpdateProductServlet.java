package com.servlet;

import com.entity.Product;
import com.service.ProductService;
import com.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    private final ProductService productService;
    private Product productToBeUpdated;

    public UpdateProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        long productId = Long.parseLong(req.getParameter("idToUpdate"));
        productToBeUpdated = productService.get(productId).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        data.put("productName", productToBeUpdated.getName());
        data.put("productPrice", productToBeUpdated.getPrice());
        data.put("productDescription", productToBeUpdated.getDescription());

        resp.getWriter().println(PageGenerator.init().getPage("updateProductPage.ftlh", data));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("productName").isEmpty() || req.getParameter("productPrice").isEmpty() || req.getParameter("productDescription").isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Product newProduct = createProductAfterUpdate(req);
            productService.update(newProduct);
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.sendRedirect("/products");
        }
    }

    private Product createProductAfterUpdate(HttpServletRequest req) {
        Date date = new Date();
        return Product.builder()
                .id(productToBeUpdated.getId())
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .description(req.getParameter("productDescription"))
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build();
    }
}
