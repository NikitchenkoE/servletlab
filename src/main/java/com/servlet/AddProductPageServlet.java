package com.servlet;

import com.entity.Product;
import com.service.ProductService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

public class AddProductPageServlet extends HttpServlet {
    ProductService productService;

    public AddProductPageServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(PageGenerator.init().getPage("addProductPage.html"));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("productName").isEmpty() || req.getParameter("productPrice").isEmpty() || req.getParameter("productDescription").isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            productService.save(createNewProduct(req));
            resp.setContentType("text/html;charset=utf-8");
            resp.sendRedirect("/products");
        }
    }

    private Product createNewProduct(HttpServletRequest req) {
        Date date = new Date();
        return Product.builder()
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .description(req.getParameter("productDescription"))
                .create(date)
                .update(date)
                .build();
    }
}
