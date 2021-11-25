package com.db;

import com.db.interfaces.CartDao;
import com.entity.Cart;
import com.mapper.CartMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCartDao implements CartDao {
    private final DataSource dataSource;
    private final String DELETE_BY_ID = "DELETE FROM cart WHERE cartItemId=?";
    private final String SAVE_TO_CART = "INSERT INTO cart (username, productName, productPrice) VALUES (?,?,?)";
    private final String GET_ALL_BY_USERNAME = "SELECT cartItemId, username, productName, productPrice FROM cart WHERE username=?";

    public JdbcCartDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void save(Cart cart) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TO_CART)) {
            preparedStatement.setString(1, cart.getUsername());
            preparedStatement.setString(2, cart.getProductName());
            preparedStatement.setDouble(3, cart.getProductPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Cart> getCart(String username) {
        List<Cart> userProductsInTheCart = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userProductsInTheCart.add(new CartMapper().mapCart(resultSet));
                }
                return userProductsInTheCart;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
