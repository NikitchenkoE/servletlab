package com.dao;

public interface SqlQueries {
    String CREATE_TABLE_PRODUCTS = "CREATE TABLE products (productID bigint, name varchar, price double precision, createDate DATETIME, updateData DATETIME)";
    String DROP_TABLE_PRODUCTS = "DROP TABLE products";
}
