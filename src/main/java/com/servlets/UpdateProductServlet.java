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
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    private final ProductDao productDao;
    private Product productToBeUpdated;

    public UpdateProductServlet(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        long productId = Long.parseLong(req.getParameter("idToUpdate"));
        productToBeUpdated = productDao.get(productId).orElseThrow(() -> new RuntimeException("Impossible to update without id"));
        data.put("productName", productToBeUpdated.getName());
        data.put("productPrice", productToBeUpdated.getPrice());

        resp.getWriter().println(PageGenerator.init().getPage("updateProductPage.html", data));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product newProduct = createProductAfterUpdate(req);
        productDao.update(newProduct);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect("/products");
    }

    private Product createProductAfterUpdate(HttpServletRequest req) {
        Date date = new Date();
        return Product.builder()
                .id(productToBeUpdated.getId())
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build();
    }
}
