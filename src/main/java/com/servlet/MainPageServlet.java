package com.servlet;

import com.ServiceLocator;
import com.entity.Product;
import com.service.MainPageService;
import com.service.ProductService;
import com.service.util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPageServlet extends HttpServlet {
    private final ProductService productService = ServiceLocator.getDependency(ProductService.class);
    private final MainPageService mainPageService = new MainPageService(productService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("products", new ArrayList<Product>());
        data.put("logged", String.valueOf(req.getAttribute("isLogged")));
        String description = req.getParameter("productDescription");

        if (req.getParameter("productDescription") == null && req.getParameter("productId") == null) {
            var products = productService.getAll();
            var productDtoList = mainPageService.mapToProductDtoList(products);
            data.put("products", productDtoList);
            resp.setStatus(HttpServletResponse.SC_OK);

        } else if (req.getParameter("productId") != null) {
            if (!req.getParameter("productId").isEmpty()) {
                long id = Long.parseLong(req.getParameter("productId"));
                data.put("products", mainPageService.getDataById(id));
                resp.setStatus(HttpServletResponse.SC_OK);
            }

        } else if (description != null && !description.isEmpty()) {
            data.put("products", mainPageService.getDataByDescription(description));

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.getWriter().println(PageGenerator.init().getPage("mainPage.ftlh", data));
    }
}
