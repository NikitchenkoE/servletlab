package com.service;

import com.dto.ProductDto;
import com.entity.Product;
import com.mapper.MapToProductDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainPageService {
    private final ProductService productService;

    public MainPageService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductDto> mapToProductDtoList(List<Product> productList) {
        MapToProductDto mapToProductDto = new MapToProductDto();
        return productList.stream()
                .map(mapToProductDto::mapToDto)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getDataById(long id) {
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

    public Map<String, Object> getDataByDescription(String description) {
        Map<String, Object> data = new HashMap<>();
        var products = productService.getByDescription(description);
        var productsDto = mapToProductDtoList(products);
        data.put("products", productsDto);
        return data;
    }
}
