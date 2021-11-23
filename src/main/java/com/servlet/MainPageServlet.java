package com.servlet;

import com.dto.ProductDto;
import com.entity.Product;
import com.mapper.MapToProductDto;
import com.service.ProductService;
import com.service.utilPageGenerator.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainPageServlet extends HttpServlet {
    private final ProductService productService;

    public MainPageServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("products", new ArrayList<Product>());
        String description = req.getParameter("productDescription");

        if (req.getParameter("productDescription") == null && req.getParameter("productId") == null) {
            var products = productService.getAll();
            var productDtoList = mapToProductDtoList(products);
            data.put("products", productDtoList);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else if (req.getParameter("productId") != null) {
            if (!req.getParameter("productId").isEmpty()) {
                long id = Long.parseLong(req.getParameter("productId"));
                data = getDataById(id);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else if (description != null && !description.isEmpty()) {
            data = getDataByDescription(description);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.getWriter().println(PageGenerator.init().getPage("mainPage.ftlh", data));
        resp.setContentType("text/html;charset=utf-8");
    }

    private List<ProductDto> mapToProductDtoList(List<Product> productList) {
        MapToProductDto mapToProductDto = new MapToProductDto();
        return productList.stream()
                .map(mapToProductDto::mapToDto)
                .collect(Collectors.toList());
    }

    private Map<String, Object> getDataById(long id) {
        Map<String, Object> data = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        List<ProductDto> productDtoList = new ArrayList<>();
        var product = productService.get(id).orElse(null);
        if (product != null) {
            productList.add(product);
            productDtoList = mapToProductDtoList(productList);
            data.put("products", productDtoList);
        } else {
            data.put("products", productDtoList);
        }
        return data;
    }

    private Map<String, Object> getDataByDescription(String description) {
        Map<String, Object> data = new HashMap<>();
        var products = productService.getByDescription(description);
        var productsDto = mapToProductDtoList(products);
        data.put("products", productsDto);
        return data;
    }


}
