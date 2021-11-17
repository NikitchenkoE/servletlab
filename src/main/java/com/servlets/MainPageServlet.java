package com.servlets;

import com.dao.ProductDao;
import com.services.PageGenerator;
import com.services.ProductTableRender;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainPageServlet extends HttpServlet {
    private final DataSource dataSource;
    private final ProductTableRender productTableRender = new ProductTableRender();

    public MainPageServlet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao productDao = new ProductDao(dataSource);
        Map<String, Object> data = new HashMap<>();
        var parameter = req.getParameter("idToDelete");
        if (parameter != null) {
            productDao.delete(Long.parseLong(parameter));
        }

        var products = productDao.getAll();
        var content = productTableRender.getProductRows(products);

        data.put("rows", content);
        resp.getWriter().println(PageGenerator.init().getPage("mainPage.html", data));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }


}
