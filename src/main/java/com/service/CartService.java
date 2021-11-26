package com.service;

import com.db.JdbcCartDao;
import com.db.JdbcCookieDao;
import com.db.JdbcProductDao;
import com.db.JdbcUserDao;
import com.dto.ProductInCartDto;
import com.entity.Cart;
import com.mapper.MapToProductInCartDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CartService {
    private final JdbcCartDao jdbcCartDao;
    private final JdbcCookieDao jdbcCookieDao;
    private final JdbcProductDao jdbcProductDao;
    private final JdbcUserDao jdbcUserDao;
    private final MapToProductInCartDto mapToProductInCartDto = new MapToProductInCartDto();

    public CartService(DataSource dataSource) {
        this.jdbcCartDao = new JdbcCartDao(dataSource);
        this.jdbcCookieDao = new JdbcCookieDao(dataSource);
        this.jdbcProductDao = new JdbcProductDao(dataSource);
        this.jdbcUserDao = new JdbcUserDao(dataSource);
    }

    public void addProductToCart(HttpServletRequest request) {
        Long productId = null;
        Cookie userCookie = findUserCookie(request);
        if (request.getParameter("productId") != null) {
            productId = Long.parseLong(request.getParameter("productId"));
        }
        if (userCookie != null) {
            var product = jdbcProductDao.get(productId).orElseThrow(() -> new RuntimeException("Product not found in db"));
            var cookie = jdbcCookieDao.get(userCookie.getValue()).orElseThrow(() -> new RuntimeException("Cookie not found in db"));
            var user = jdbcUserDao.findByUsername(cookie.getUsername()).orElseThrow(() -> new RuntimeException("User not fond in db"));
            jdbcCartDao.save(Cart.builder()
                    .userId(user.getId())
                    .productId(product.getId())
                    .build());
        }
    }

    public void deleteOneProductFromTheCart(long id) {
        log.info("Deleted item with id {}",id);
        jdbcCartDao.delete(id);
    }

    public void deleteAllProductWithSameIdFromCart(long id) {
        log.info("deleted all items with id {}",id);
        jdbcCartDao.deleteByProductId(id);
    }

    public List<ProductInCartDto> findAllProductInCart(HttpServletRequest request) {
        List<ProductInCartDto> productInCartList = new ArrayList<>();
        Cookie userCookie = findUserCookie(request);
        var cookie = jdbcCookieDao.get(userCookie.getValue()).orElseThrow(() -> new RuntimeException("Cookie not found in db"));
        var user = jdbcUserDao.findByUsername(cookie.getUsername()).orElseThrow(() -> new RuntimeException("User not fond in db"));
        var userCart = jdbcCartDao.getCart(user.getId());
        for (Cart cart : userCart) {
            jdbcProductDao.get(cart.getProductId())
                    .ifPresent(product -> productInCartList.add(mapToProductInCartDto.mapToProductInCart(cart, product)));
        }
        return productInCartList;
    }

    public double sumAllProducts(HttpServletRequest request) {
        double result = 0.0;
        var products = findAllProductInCart(request);
        for (ProductInCartDto product : products) {
            result += product.getProductPrice();
        }
        return result;
    }

    private Cookie findUserCookie(HttpServletRequest request) {
        Cookie userCookie = null;
        var cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user-token")) {
                userCookie = cookie;
            }
        }
        return userCookie;
    }


}
