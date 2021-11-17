package com.db;

import com.constants.ProductsConstants;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private final DataSource dataSource;

    public TableCreator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTableProducts(){
        try (Connection connection =dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(ProductsConstants.CREATE_TABLE);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
