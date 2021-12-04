package com.db.jdbc;

import com.db.interfaces.CartDao;
import com.db.mapper.CartProductMapper;
import com.entity.ProductInCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Slf4j
public class JdbcCartDao implements CartDao {
    private JdbcTemplate jdbcTemplate;
    private final String DELETE_BY_ID = "DELETE FROM cart WHERE cartItemId=?";
    private final String DELETE_BY_PRODUCT_ID = "DELETE FROM cart WHERE productId=?";
    private final String SAVE_TO_CART = "INSERT INTO cart (userId, productId) VALUES (?,?)";
    private final String GET_ALL_BY_USER_ID = "SELECT cartItemId, userId, productId FROM cart WHERE userId=%s";

    @Override
    public void delete(long id) {
        log.info("Delete cartElement by id{}", id);
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void deleteByProductId(long id) {
        log.info("Delete product from cart by id{}", id);
        jdbcTemplate.update(DELETE_BY_PRODUCT_ID, id);
    }

    @Override
    public void save(ProductInCart productInCart) {
        log.info("Saved {}", productInCart);
        jdbcTemplate.update(SAVE_TO_CART, productInCart.getUserId(), productInCart.getProductId());
    }

    @Override
    public List<ProductInCart> getCart(long userId) {
        log.info("Get user cart with id {}", userId);
        return jdbcTemplate.query(String.format(GET_ALL_BY_USER_ID, userId), (rs, rowNum) -> new CartProductMapper().mapProductInCart(rs));
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
