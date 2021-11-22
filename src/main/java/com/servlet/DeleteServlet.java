package com.servlet;

import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private final ProductDao productDao;

    public DeleteServlet(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var parameter = req.getParameter("idToDelete");
        if (parameter != null) {
            productDao.delete(Long.parseLong(parameter));
            resp.setContentType("text/html;charset=utf-8");
            resp.sendRedirect("/products");
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
