package com.service;

import com.db.interfaces.CartDao;
import com.db.interfaces.ProductDao;
import com.entity.Product;
import com.entity.dto.ProductDto;
import com.entity.mapper.MapToProductDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProductService {
    private ProductDao productDao;
    private CartDao cartDao;

    public void save(Product product) {
        productDao.save(createNewProduct(product));
    }

    public void delete(long parseLong) {
        productDao.delete(parseLong);
        cartDao.deleteByProductId(parseLong);
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public Optional<Product> get(long productId) {
        return productDao.get(productId);
    }

    public void update(Product productToBeUpdated, Product product) {
        Date date = new Date();

        productDao.update(Product.builder()
                .id(productToBeUpdated.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .create(productToBeUpdated.getCreate())
                .update(date)
                .build());
    }

    public List<Product> getByDescription(String description) {
        return productDao.getByDescription(description);
    }

    private Product createNewProduct(Product product) {
        Date date = new Date();
        return Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .create(date)
                .update(date)
                .build();
    }

    public List<ProductDto> mapToProductDtoList(List<Product> productList) {
        MapToProductDto mapToProductDto = new MapToProductDto();
        return productList.stream()
                .map(mapToProductDto::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsById(long id) {
        log.info("Get all data by id {}", id);
        List<Product> productList = new ArrayList<>();
        List<ProductDto> productDtoList = new ArrayList<>();
        var product = get(id);
        if (product.isPresent()) {
            productList.add(product.get());
            productDtoList = mapToProductDtoList(productList);
        }
        return productDtoList;
    }

    public List<ProductDto> getProductsByDescription(String description) {
        log.info("Get data by description {}", description);
        var products = getByDescription(description);
        return mapToProductDtoList(products);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }
}
