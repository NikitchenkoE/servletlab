package com.db.jdbc;

import com.db.interfaces.CartDao;
import com.entity.ProductInCart;
import com.db.mapper.CartProductMapper;
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
    private DataSource dataSource;
    private final String DELETE_BY_ID = "DELETE FROM cart WHERE cartItemId=?";
    private final String DELETE_BY_PRODUCT_ID = "DELETE FROM cart WHERE productId=?";
    private final String SAVE_TO_CART = "INSERT INTO cart (userId, productId) VALUES (?,?)";
    private final String GET_ALL_BY_USERNAME = "SELECT cartItemId, userId, productId FROM cart WHERE userId=?";

    @Override
    public void delete(long id) {
        log.info("Delete cartElement by id{}", id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Problem when deleting cart with id {}", id, exception);
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
            log.error("Problem when deleting product with id {}", id, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void save(ProductInCart productInCart) {
        log.info("Saved {}", productInCart);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TO_CART)) {
            preparedStatement.setLong(1, productInCart.getUserId());
            preparedStatement.setLong(2, productInCart.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Problem when saving cart {}", productInCart, exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<ProductInCart> getCart(long userId) {
        log.info("Get user cart with id {}", userId);
        List<ProductInCart> userProductsInTheCart = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USERNAME)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userProductsInTheCart.add(new CartProductMapper().mapProductInCart(resultSet));
                }
                return userProductsInTheCart;
            }
        } catch (SQLException exception) {
            log.error("Problem when getting user cart with user id {}", userId, exception);
            throw new RuntimeException(exception);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
