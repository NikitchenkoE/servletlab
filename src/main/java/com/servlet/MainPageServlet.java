package com.servlet;

import com.entity.Product;
import com.service.MainPageService;
import com.service.ProductService;
import com.service.SecurityService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPageServlet extends HttpServlet {
    private ProductService productService;
    private SecurityService securityService;
    private MainPageService mainPageService;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
        securityService = (SecurityService) getServletContext().getAttribute("securityService");
        mainPageService = new MainPageService(productService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("products", new ArrayList<Product>());
        data.put("logged", String.valueOf(securityService.isLogged(req)));
        String description = req.getParameter("productDescription");

        if (req.getParameter("productDescription") == null && req.getParameter("productId") == null) {
            var products = productService.getAll();
            var productDtoList = mainPageService.mapToProductDtoList(products);
            data.put("products", productDtoList);
            resp.setStatus(HttpServletResponse.SC_OK);

        } else if (req.getParameter("productId") != null) {
            if (!req.getParameter("productId").isEmpty()) {
                long id = Long.parseLong(req.getParameter("productId"));
                data = mainPageService.getDataById(id);
                data.put("logged", String.valueOf(securityService.isLogged(req)));
                resp.setStatus(HttpServletResponse.SC_OK);
            }

        } else if (description != null && !description.isEmpty()) {
            data = mainPageService.getDataByDescription(description);
            data.put("logged", String.valueOf(securityService.isLogged(req)));

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.getWriter().println(PageGenerator.init().getPage("mainPage.ftlh", data));
    }
}
