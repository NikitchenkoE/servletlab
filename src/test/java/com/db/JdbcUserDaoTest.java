package com.db;

import com.db.jdbc.JdbcUserDao;
import com.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoTest {
    DataSourceFactory dataSourceFactory = new DataSourceFactory("jdbc:h2:mem:testdb", "user", "user");
    JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSourceFactory.getDataSource());
    User user1 = new User(1L,"user1","soledPassword1","sole1");
    User user2 = new User(2L,"user2","soledPassword2","sole2");
    User user3 = new User(3L,"user3","soledPassword3","sole3+");

    @BeforeEach
    void init() {
        jdbcUserDao.save(user1);
        jdbcUserDao.save(user2);
        jdbcUserDao.save(user3);
    }

    @AfterEach
    void dropTable() {
        try (Connection connection = dataSourceFactory.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.DROP_TABLE_USERS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_PRODUCTS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_SESSIONS);
            statement.executeUpdate(SqlQueries.DROP_TABLE_CART);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Test
    void testGetUserOrElseNull() {
        User userEntity1FromDb = jdbcUserDao.get(1).orElse(null);
        User userEntity2FromDb = jdbcUserDao.get(2).orElse(null);
        User userEntity3FromDb = jdbcUserDao.get(3).orElse(null);
        User userEntity4FromDb = jdbcUserDao.get(4).orElse(null);
        assertEquals(user1.toString(), Objects.requireNonNull(userEntity1FromDb).toString());
        assertEquals(user2.toString(), Objects.requireNonNull(userEntity2FromDb).toString());
        assertEquals(user3.toString(), Objects.requireNonNull(userEntity3FromDb).toString());
        assertNull(userEntity4FromDb);
    }

    @Test
    void getAll() {
        ArrayList<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);

        var actual = jdbcUserDao.getAll();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void update() {
        User beforeUpdate = jdbcUserDao.get(2).orElse(null);
        User toUpdate = new User(2L,"newName","newPassword","newSole");
        jdbcUserDao.update(toUpdate);

        User updatedUser = jdbcUserDao.get(2).orElse(null);
        assertEquals(user2.toString(), Objects.requireNonNull(beforeUpdate).toString());
        assertEquals(toUpdate.toString(), Objects.requireNonNull(updatedUser).toString());
    }

    @Test
    void delete() {
        assertNotNull(jdbcUserDao.get(1).orElse(null));
        assertNotNull(jdbcUserDao.get(2).orElse(null));
        assertNotNull(jdbcUserDao.get(3).orElse(null));
        jdbcUserDao.delete(1);
        jdbcUserDao.delete(2);
        jdbcUserDao.delete(3);
        assertNull(jdbcUserDao.get(1).orElse(null));
        assertNull(jdbcUserDao.get(2).orElse(null));
        assertNull(jdbcUserDao.get(3).orElse(null));
    }
    @Test
    void findByUsername(){
        assertEquals(user1.toString(),jdbcUserDao.findByUsername("user1").get().toString());
        assertEquals(user2.toString(),jdbcUserDao.findByUsername("user2").get().toString());
        assertEquals(user3.toString(),jdbcUserDao.findByUsername("user3").get().toString());
    }
}