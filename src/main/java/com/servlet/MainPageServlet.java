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

        String description = req.getParameter("productDescription");

        if (req.getParameter("productDescription") == null && req.getParameter("productId") == null) {
            var products = productService.getAll();
            var productsDto = mapToProductDtoList(products);
            data.put("products", productsDto);
        } else if (req.getParameter("productId") != null) {
            List<Product> productList = new ArrayList<>();
            long id = Long.parseLong(req.getParameter("productId"));
            var product = productService.get(id).orElseThrow(() -> new RuntimeException("Cant be null"));
            productList.add(product);
            var productsDto = mapToProductDtoList(productList);
            data.put("products", productsDto);
        } else if (description != null) {
            var products = productService.getByDescription(description);
            var productsDto = mapToProductDtoList(products);
            data.put("products", productsDto);
        } else {
            resp.getWriter().println(PageGenerator.init().getPage("mainPage.ftlh"));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        resp.getWriter().println(PageGenerator.init().getPage("mainPage.ftlh", data));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    private List<ProductDto> mapToProductDtoList(List<Product> productList) {
        MapToProductDto mapToProductDto = new MapToProductDto();
        return productList.stream()
                .map(mapToProductDto::mapToDto)
                .collect(Collectors.toList());
    }


}
