package com.service;

import com.db.interfaces.CartDao;
import com.db.interfaces.ProductDao;
import com.entity.ProductInCart;
import com.entity.User;
import com.entity.dto.ProductInCartDto;
import com.entity.mapper.MapToProductInCartDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CartService {
    private CartDao cartDao;
    private ProductDao productDao;
    private final MapToProductInCartDto mapToProductInCartDto = new MapToProductInCartDto();



    public void addProductToCart(User user, String productId) {
        cartDao.save(ProductInCart.builder()
                .userId(user.getId())
                .productId(Long.valueOf(productId))
                .build());

    }

    public void deleteOneProductFromTheCart(long id) {
        log.info("Deleted item with id {}", id);
        cartDao.delete(id);
    }

    public void deleteAllProductWithSameIdFromCart(long id) {
        log.info("deleted all items with id {}", id);
        cartDao.deleteByProductId(id);
    }

    public List<ProductInCartDto> findAllProductInCart(User user) {
        List<ProductInCartDto> productInCartList = new ArrayList<>();
        var userCart = cartDao.getCart(user.getId());
        for (ProductInCart productInCart : userCart) {
            productDao.get(productInCart.getProductId())
                    .ifPresent(product -> productInCartList.add(mapToProductInCartDto.mapToProductInCart(productInCart, product)));
        }
        return productInCartList;
    }

    public double sumAllProducts(User user) {
        double result = 0.0;
        var products = findAllProductInCart(user);
        for (ProductInCartDto product : products) {
            result += product.getProductPrice();
        }
        return result;
    }

    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
