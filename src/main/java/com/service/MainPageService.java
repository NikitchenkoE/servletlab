package com.service;

import com.entity.dto.ProductDto;
import com.entity.Product;
import com.entity.mapper.MapToProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MainPageService {
    private ProductService productService;

    public List<ProductDto> mapToProductDtoList(List<Product> productList) {
        MapToProductDto mapToProductDto = new MapToProductDto();
        return productList.stream()
                .map(mapToProductDto::mapToDto)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getDataById(long id) {
        log.info("Get all data by id {}", id);
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
        log.info("Get data by description {}", description);
        Map<String, Object> data = new HashMap<>();
        var products = productService.getByDescription(description);
        var productsDto = mapToProductDtoList(products);
        data.put("products", productsDto);
        return data;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
