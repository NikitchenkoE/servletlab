package com.servlet;

import com.service.LoginService;
import com.service.ProductService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductPageServlet extends HttpServlet {
    ProductService productService;
    LoginService loginService;

    public AddProductPageServlet(ProductService productService, LoginService loginService) {
        this.productService = productService;
        this.loginService = loginService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            Map<String, Object> data = new HashMap<>();
            data.put("logged", String.valueOf(loginService.isLogged(req)));
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
