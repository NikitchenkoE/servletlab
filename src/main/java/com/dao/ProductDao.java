package com.dao;

import com.dao.interfaces.ProductDaoInterface;
import com.entity.Product;
import com.maper.ProductMapper;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductDao implements ProductDaoInterface {
    String SELECT_BY_ID = "SELECT productID, name, price, createDate, updateDate FROM products WHERE productId=?";
    String SELECT_ALL = "SELECT productID, name, price, createDate, updateDate FROM products";
    String INSERT_INTO_TABLE = "INSERT INTO products(name, price, createDate, updateDate) VALUES (?,?,?,?)";
    String UPDATE_BY_ID = "UPDATE products SET productId=?, name=?, price=?, createDate=?, updateDate=? WHERE productId=?";
    String DELETE_BY_ID = "DELETE FROM products WHERE productId=?";

    private final DataSource dataSource;
    private final ProductMapper productMapper = new ProductMapper();

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Product> get(long id) {
        log.info("get product by {}", id);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.ofNullable(productMapper.mapProduct(resultSet));
                    } else return Optional.empty();
                }
            }
        } catch (SQLException throwables) {
            log.error("Error by getting product by id {}", id);
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<Product> getAll() {
        log.info("get all products");
        List<Product> productEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                productEntities.add(productMapper.mapProduct(resultSet));
            }
        } catch (SQLException throwables) {
            log.error("Problem to find all products");
            throw new RuntimeException(throwables);
        }
        return productEntities;
    }

    @Override
    public void save(Product productEntity) {
        log.info("Saving product {}", productEntity.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TABLE)) {
                preparedStatement.setString(1, productEntity.getName());
                preparedStatement.setDouble(2, productEntity.getPrice());
                preparedStatement.setTimestamp(3, new Timestamp(productEntity.getCreate().getTime()));
                preparedStatement.setTimestamp(4, new Timestamp(productEntity.getUpdate().getTime()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            log.error("Problem when saving {}", productEntity);
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public void update(Product productEntity) {
        log.info("Updating product {}", productEntity.toString());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setLong(1, productEntity.getId());
            preparedStatement.setString(2, productEntity.getName());
            preparedStatement.setDouble(3, productEntity.getPrice());
            preparedStatement.setTimestamp(4, new Timestamp(productEntity.getCreate().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(productEntity.getUpdate().getTime()));
            preparedStatement.setLong(6, productEntity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            log.error("Problem when updating {}", productEntity);
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public void delete(long id) {
        log.info("delete product by {}", id);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            log.error("Error by deleting product by id {}", id);
            throw new RuntimeException(throwables);
        }
    }
}
