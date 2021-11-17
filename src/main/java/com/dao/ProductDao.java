package com.dao;

import com.entity.ProductEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<ProductEntity> {
    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<ProductEntity> get(long id) {
        String sql = "SELECT * FROM products WHERE productId=?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    ProductEntity productEntity = ProductEntity.builder()
                            .id(resultSet.getLong(1))
                            .name(resultSet.getString(2))
                            .price(resultSet.getDouble(3))
                            .create(new Date(resultSet.getTimestamp(4).getTime()))
                            .update(new Date(resultSet.getTimestamp(5).getTime()))
                            .build();
                    return Optional.ofNullable(productEntity);
                } else return Optional.empty();

            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<ProductEntity> getAll() {
        String sql = "SELECT * FROM products";
        List<ProductEntity> productEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ProductEntity productEntity = ProductEntity.builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .price(resultSet.getDouble(3))
                        .create(new Date(resultSet.getTimestamp(4).getTime()))
                        .update(new Date(resultSet.getTimestamp(5).getTime()))
                        .build();
                productEntities.add(productEntity);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return productEntities;
    }

    @Override
    public void save(ProductEntity productEntity) {
        try (Connection connection = dataSource.getConnection()) {

            String sql = "INSERT INTO products(productID, name, price, createDate, updateData) VALUES (?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, productEntity.getId());
                preparedStatement.setString(2, productEntity.getName());
                preparedStatement.setDouble(3, productEntity.getPrice());
                preparedStatement.setTimestamp(4, new Timestamp(productEntity.getCreate().getTime()));
                preparedStatement.setTimestamp(5, new Timestamp(productEntity.getUpdate().getTime()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public void update(ProductEntity productEntity) {
        String sql = "UPDATE products SET productId=?, name=?, price=?, createDate=?, updateData=? WHERE productId=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, productEntity.getId());
            preparedStatement.setString(2, productEntity.getName());
            preparedStatement.setDouble(3, productEntity.getPrice());
            preparedStatement.setTimestamp(4, new Timestamp(productEntity.getCreate().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(productEntity.getUpdate().getTime()));
            preparedStatement.setLong(6, productEntity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM products WHERE productId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
