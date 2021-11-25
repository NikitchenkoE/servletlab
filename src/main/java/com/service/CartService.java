package com.service;

import com.db.JdbcCartDao;
import com.db.JdbcCookieDao;
import com.db.JdbcProductDao;
import com.entity.Cart;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.sql.DataSource;
import java.util.List;

public class CartService {
    private final JdbcCartDao jdbcCartDao;
    private final JdbcCookieDao jdbcCookieDao;
    private final JdbcProductDao jdbcProductDao;

    public CartService(DataSource dataSource) {
        this.jdbcCartDao = new JdbcCartDao(dataSource);
        this.jdbcCookieDao = new JdbcCookieDao(dataSource);
        this.jdbcProductDao = new JdbcProductDao(dataSource);
    }

    public void addProductToCart(HttpServletRequest request) {
        Long productId = null;
        Cookie userCookie = findUserCookie(request);
        if (request.getParameter("productId") != null) {
            productId = Long.parseLong(request.getParameter("productId"));
        }
        if (productId != null && userCookie != null) {
            var product = jdbcProductDao.get(productId).orElseThrow(() -> new RuntimeException("Product not found in db"));
            var cookie = jdbcCookieDao.get(userCookie.getValue()).orElseThrow(() -> new RuntimeException("Cookie not found in db"));
            jdbcCartDao.save(Cart.builder()
                    .username(cookie.getUsername())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .build());
        }
    }

    public void deleteProductFromTheCart(long id) {
        jdbcCartDao.delete(id);
    }

    public List<Cart> findAllProductInCart(HttpServletRequest request) {
        Cookie userCookie = findUserCookie(request);
        var cookie = jdbcCookieDao.get(userCookie.getValue()).orElseThrow(() -> new RuntimeException("Cookie not found in db"));
        return jdbcCartDao.getCart(cookie.getUsername());
    }

    public double sumAllProducts(HttpServletRequest request){
        double result = 0.0;
        var products = findAllProductInCart(request);
        for (Cart product : products) {
            result = result + product.getProductPrice();
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
