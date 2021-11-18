package com.servlets;

import com.dao.ProductDao;
import com.entity.Product;
import com.services.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;

public class AddProductPageServlet extends HttpServlet {
    private final ProductDao productDao;

    public AddProductPageServlet(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(PageGenerator.init().getPage("addProductPage.html"));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productDao.save(createNewProduct(req));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect("/products");
    }

    private Product createNewProduct(HttpServletRequest req) {
        Date date = new Date();
        return Product.builder()
                .id(null)
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .create(date)
                .update(date)
                .build();
    }
}
