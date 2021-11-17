package com.servlets;

import com.dao.ProductDao;
import com.entity.ProductEntity;
import com.services.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;

public class AddProductPageServlet extends HttpServlet {
    private final DataSource dataSource;

    public AddProductPageServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(PageGenerator.init().getPage("addProductPage.html", null));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao productDao = new ProductDao(dataSource);

        productDao.save(createNewProduct(req, productDao));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect("/products");
    }

    private ProductEntity createNewProduct(HttpServletRequest req, ProductDao productDao) {
        Date date = new Date();
        return ProductEntity.builder()
                .id(productDao.getAll().size() + 1L)
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .create(date)
                .update(date)
                .build();
    }
}
