package com.service;

import com.entity.Product;
import com.entity.dto.ProductDto;
import com.entity.mapper.MapToProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<ProductDto> getDataById(long id) {
        log.info("Get all data by id {}", id);
        List<Product> productList = new ArrayList<>();
        List<ProductDto> productDtoList = new ArrayList<>();
        var product = productService.get(id).orElse(null);
        if (product != null) {
            productList.add(product);
            productDtoList = mapToProductDtoList(productList);
        }
        return productDtoList;
    }

    public List<ProductDto> getDataByDescription(String description) {
        log.info("Get data by description {}", description);
        var products = productService.getByDescription(description);
        return mapToProductDtoList(products);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
