package com.db;

import com.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    DataSourceFactory dataConnectionPull = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    JdbcProductDao productDao = new JdbcProductDao(dataConnectionPull.getDataSource());
    Product productEntity1 = new Product(1L, "first", 15.0,"description", new Date(), new Date());
    Product productEntity2 = new Product(2L, "second", 25.0,"description", new Date(), new Date());
    Product productEntity3 = new Product(3L, "third", 35.0,"description", new Date(), new Date());

    @BeforeEach
    void init() {
            productDao.save(productEntity1);
            productDao.save(productEntity2);
            productDao.save(productEntity3);
    }

    @AfterEach
    void dropTable() {
        try (Connection connection = dataConnectionPull.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_USERS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_COOKIES);
            statement.executeUpdate(SqlQueries.DROP_TABLE_CART);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getFromDbShouldReturnThreeRealResultAndOneNull() {
        Product productEntity1FromDb = productDao.get(1).orElse(null);
        Product productEntity2FromDb = productDao.get(2).orElse(null);
        Product productEntity3FromDb = productDao.get(3).orElse(null);
        Product productEntity4FromDb = productDao.get(4).orElse(null);
        assertEquals(productEntity1.toString(), Objects.requireNonNull(productEntity1FromDb).toString());
        assertEquals(productEntity2.toString(), Objects.requireNonNull(productEntity2FromDb).toString());
        assertEquals(productEntity3.toString(), Objects.requireNonNull(productEntity3FromDb).toString());
        assertNull(productEntity4FromDb);
    }

    @Test
    void getAllProductsByBD() {
        ArrayList<Product> expected = new ArrayList<>();
        expected.add(productEntity1);
        expected.add(productEntity2);
        expected.add(productEntity3);

        var actual = productDao.getAll();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void saveToBdShouldSave() throws SQLException {
        Product productEntityToSave = new Product(4L, "first", 15.0,"description", new Date(), new Date());
        String sql = "SELECT * FROM products WHERE productId=4";
        productDao.save(productEntityToSave);
        Product productEntity;
        try (Connection connection = dataConnectionPull.getDataSource().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                productEntity = Product.builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .price(resultSet.getDouble(3))
                        .description(resultSet.getString(4))
                        .create(new Date(resultSet.getTimestamp(5).getTime()))
                        .update(new Date(resultSet.getTimestamp(6).getTime()))
                        .build();

            }
        }
        assertEquals(productEntityToSave.toString(), productEntity.toString());
    }

    @Test
    void updateProductInBD() {
        Product beforeUpdate = productDao.get(2).orElse(null);
        Product productEntityToUpdate = new Product(2L, "updated", 20.0,"description", new Date(), new Date());
        productDao.update(productEntityToUpdate);

        Product updatedProductEntity = productDao.get(2).orElse(null);
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

    @Test
    void testFindByDescription(){
        List<Product> expected = new ArrayList<>();
        expected.add(productEntity1);
        expected.add(productEntity2);
        expected.add(productEntity3);

        List<Product> actual = productDao.getByDescription("description");
        assertEquals(expected.toString(),actual.toString());
    }

    @Test
    void testFindByPartOfDescription(){
        List<Product> expected = new ArrayList<>();
        expected.add(productEntity1);
        expected.add(productEntity2);
        expected.add(productEntity3);

        List<Product> actual = productDao.getByDescription("desc");
        assertEquals(expected.toString(),actual.toString());
    }
}