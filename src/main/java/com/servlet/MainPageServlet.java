package com.servlet;

import com.dao.ProductDao;
import com.dto.ProductDto;
import com.entity.Product;
import com.maper.MapToProductDto;
import com.service.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainPageServlet extends HttpServlet {
    private final ProductDao productDao;

    public MainPageServlet(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        var products = productDao.getAll();
        var productsDto = mapToProductDtoList(products);

        data.put("products", productsDto);
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
