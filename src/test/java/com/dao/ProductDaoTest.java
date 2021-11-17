package com.dao;

import com.db.DataConnectionPull;
import com.entity.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    DataConnectionPull dataConnectionPull = new DataConnectionPull("jdbc:h2:mem:testdb", "user", "user");
    ProductDao productDao = new ProductDao(dataConnectionPull.getDataSource());
    ProductEntity productEntity1 = new ProductEntity(1L, "first", 15.0, new Date(), new Date());
    ProductEntity productEntity2 = new ProductEntity(2L, "second", 25.0, new Date(), new Date());
    ProductEntity productEntity3 = new ProductEntity(3L, "third", 35.0, new Date(), new Date());

    @BeforeEach
    void createTable() {
        try (Connection connection = dataConnectionPull.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.CREATE_TABLE_PRODUCTS);
            productDao.save(productEntity1);
            productDao.save(productEntity2);
            productDao.save(productEntity3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    void dropTable() {
        try (Connection connection = dataConnectionPull.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getFromDbShouldReturnThreeRealResultAndOneNull() {
        ProductEntity productEntity1FromDb = productDao.get(1).orElse(null);
        ProductEntity productEntity2FromDb = productDao.get(2).orElse(null);
        ProductEntity productEntity3FromDb = productDao.get(3).orElse(null);
        ProductEntity productEntity4FromDb = productDao.get(4).orElse(null);
        assertEquals(productEntity1.toString(), Objects.requireNonNull(productEntity1FromDb).toString());
        assertEquals(productEntity2.toString(), Objects.requireNonNull(productEntity2FromDb).toString());
        assertEquals(productEntity3.toString(), Objects.requireNonNull(productEntity3FromDb).toString());
        assertNull(productEntity4FromDb);
    }

    @Test
    void getAllProductsByBD() {
        ArrayList<ProductEntity> expected = new ArrayList<>();
        expected.add(productEntity1);
        expected.add(productEntity2);
        expected.add(productEntity3);

        var actual = productDao.getAll();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void saveToBdShouldSave() throws SQLException {
        ProductEntity productEntityToSave = new ProductEntity(25L, "first", 15.0, new Date(), new Date());
        String sql = "SELECT * FROM products WHERE productId=25";
        productDao.save(productEntityToSave);
        ProductEntity productEntity;
        try (Connection connection = dataConnectionPull.getDataSource().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                productEntity = ProductEntity.builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .price(resultSet.getDouble(3))
                        .create(new Date(resultSet.getTimestamp(4).getTime()))
                        .update(new Date(resultSet.getTimestamp(5).getTime()))
                        .build();

            }
        }
        assertEquals(productEntityToSave.toString(), productEntity.toString());
    }

    @Test
    void updateProductInBD() {
        ProductEntity beforeUpdate = productDao.get(2).orElse(null);
        ProductEntity productEntityToUpdate = new ProductEntity(2L, "updated", 20.0, new Date(), new Date());
        productDao.update(productEntityToUpdate);

        ProductEntity updatedProductEntity = productDao.get(2).orElse(null);
        assertEquals(productEntity2.toString(), Objects.requireNonNull(beforeUpdate).toString());
        assertEquals(productEntityToUpdate.toString(), Objects.requireNonNull(updatedProductEntity).toString());
    }

    @Test
    void deleteProductFromBd() {
        assertNotNull(productDao.get(1).orElse(null));
        assertNotNull(productDao.get(2).orElse(null));
        assertNotNull(productDao.get(3).orElse(null));
        productDao.delete(1);
        productDao.delete(2);
        productDao.delete(3);
        assertNull(productDao.get(1).orElse(null));
        assertNull(productDao.get(2).orElse(null));
        assertNull(productDao.get(3).orElse(null));
    }
}