package com.mapper;

import com.dto.ProductInCartDto;
import com.entity.Cart;
import com.entity.Product;

public class MapToProductInCartDto {
    public ProductInCartDto mapToProductInCart(Cart cart, Product product) {
        return ProductInCartDto.builder()
                .cartId(cart.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build();
    }
}
