package com.constants;

public interface ProductsConstants {
    String CREATE_TABLE = "CREATE TABLE products (productID bigint, name varchar, price double precision, createDate timestamp, updateData timestamp)";
    String SELECT_BY_ID = "SELECT * FROM products WHERE productId=?";
    String SELECT_ALL = "SELECT * FROM products";
    String INSERT_INTO_TABLE = "INSERT INTO products(productID, name, price, createDate, updateData) VALUES (?,?,?,?,?)";
    String UPDATE_BY_ID = "UPDATE products SET productId=?, name=?, price=?, createDate=?, updateData=? WHERE productId=?";
    String DELETE_BY_ID = "DELETE FROM products WHERE productId=?";
}
