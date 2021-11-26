package com.db;

import com.db.interfaces.CartDao;
import com.entity.Cart;
import com.mapper.CartMapper;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcCartDao implements CartDao {
    private final DataSource dataSource;
    private final String DELETE_BY_ID = "DELETE FROM cart WHERE cartItemId=?";
    private final String DELETE_BY_PRODUCT_ID = "DELETE FROM cart WHERE productId=?";
    private final String SAVE_TO_CART = "INSERT INTO cart (userId, productId) VALUES (?,?)";
    private final String GET_ALL_BY_USERNAME = "SELECT cartItemId, userId, productId FROM cart WHERE userId=?";

    public JdbcCartDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void delete(long id) {
        log.info("Delete cartElement by id{}", id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Problem when deleting cart with id {}", id);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deleteByProductId(long id) {
        log.info("Delete product from cart by id{}", id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_PRODUCT_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Problem when deleting product with id {}", id);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void save(Cart cart) {
        log.info("Saved {}", cart);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TO_CART)) {
            preparedStatement.setLong(1, cart.getUserId());
            preparedStatement.setLong(2, cart.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Problem when saving cart {}", cart);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Cart> getCart(long userId) {
        log.info("Get user cart with id {}", userId);
        List<Cart> userProductsInTheCart = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USERNAME)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userProductsInTheCart.add(new CartMapper().mapCart(resultSet));
                }
                return userProductsInTheCart;
            }
        } catch (SQLException exception) {
            log.error("Problem when getting user cart with user id {} {}", userId);
            throw new RuntimeException(exception);
        }
    }
}
