package com.services;

import com.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTableRenderTest {
    ProductTableRender productTableRender = new ProductTableRender();
    Date date = new Date();
    Product productEntity1 = new Product(1L, "first", 15.0, date, date);
    Product productEntity2 = new Product(2L, "second", 25.0, date, date);


    @Test
    void TestGetProductRowsShouldReturnPartOfTable() {
        List<Product> productEntityList = new ArrayList<>();
        productEntityList.add(productEntity1);
        productEntityList.add(productEntity2);
        String content = productTableRender.getProductRows(productEntityList);
        String expected = "<tr>\n" +
                "<td>1</td>\n" +
                "<td>first</td>\n" +
                "<td>15.0</td>\n" +
                "<td>" + date.toString() + "</td>\n" +
                "<td>" + date.toString() + "</td>\n" +
                "<td><a href=\"http://localhost:8080/products?idToDelete=1\">Delete</a></td>\n" +
                "<td><a href=\"http://localhost:8080/products/update?idToUpdate=1\">Update</a></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>2</td>\n" +
                "<td>second</td>\n" +
                "<td>25.0</td>\n" +
                "<td>" + date.toString() + "</td>\n" +
                "<td>" + date.toString() + "</td>\n" +
                "<td><a href=\"http://localhost:8080/products?idToDelete=2\">Delete</a></td>\n" +
                "<td><a href=\"http://localhost:8080/products/update?idToUpdate=2\">Update</a></td>\n" +
                "</tr>";

        assertEquals(expected, content);
    }
}