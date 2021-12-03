package com.entity.mapper;

import com.entity.dto.ProductInCartDto;
import com.entity.ProductInCart;
import com.entity.Product;

public class MapToProductInCartDto {
    public ProductInCartDto mapToProductInCart(ProductInCart cart, Product product) {
        return ProductInCartDto.builder()
                .cartItemId(cart.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build();
    }
}
