package com.dao;

public interface SqlQueries {
    String CREATE_TABLE_PRODUCTS = "CREATE TABLE products (productID IDENTITY NOT NULL PRIMARY KEY, name varchar, price double precision, description varchar, createDate timestamp, updateData timestamp)";
    String DROP_TABLE_PRODUCTS = "DROP TABLE products";
}
