package com.service;

import com.db.jdbc.JdbcCartDao;
import com.db.jdbc.JdbcProductDao;
import com.dto.ProductInCartDto;
import com.entity.Cart;
import com.entity.User;
import com.mapper.MapToProductInCartDto;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CartService {
    private final JdbcCartDao jdbcCartDao;
    private final JdbcProductDao jdbcProductDao;
    private final MapToProductInCartDto mapToProductInCartDto = new MapToProductInCartDto();

    public CartService(DataSource dataSource) {
        this.jdbcCartDao = new JdbcCartDao(dataSource);
        this.jdbcProductDao = new JdbcProductDao(dataSource);
    }

    public void addProductToCart(User user, String productId) {
        jdbcCartDao.save(Cart.builder()
                .userId(user.getId())
                .productId(Long.valueOf(productId))
                .build());

    }

    public void deleteOneProductFromTheCart(long id) {
        log.info("Deleted item with id {}", id);
        jdbcCartDao.delete(id);
    }

    public void deleteAllProductWithSameIdFromCart(long id) {
        log.info("deleted all items with id {}", id);
        jdbcCartDao.deleteByProductId(id);
    }

    public List<ProductInCartDto> findAllProductInCart(User user) {
        List<ProductInCartDto> productInCartList = new ArrayList<>();
        var userCart = jdbcCartDao.getCart(user.getId());
        for (Cart cart : userCart) {
            jdbcProductDao.get(cart.getProductId())
                    .ifPresent(product -> productInCartList.add(mapToProductInCartDto.mapToProductInCart(cart, product)));
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

}
