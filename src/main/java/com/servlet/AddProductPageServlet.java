package com.servlet;

import com.service.ProductService;
import com.service.SecurityService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductPageServlet extends HttpServlet {
    private ProductService productService;
    private SecurityService securityService;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            Map<String, Object> data = new HashMap<>();
            data.put("logged", String.valueOf(securityService.isLogged(req)));
            resp.getWriter().println(PageGenerator.init().getPage("addProductPage.ftlh", data));
            resp.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("productName").isEmpty() || req.getParameter("productPrice").isEmpty() || req.getParameter("productDescription").isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            productService.save(req);
            resp.sendRedirect("/products");
        }
    }
}
