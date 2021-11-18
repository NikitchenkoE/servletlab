package com.services;

import com.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.StringJoiner;

@Slf4j
public class ProductTableRender {

    public String getProductRows(List<ProductEntity> productEntities) {
        log.info("Created table with products {}", productEntities.toString());
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (ProductEntity productEntity : productEntities) {
            stringJoiner.add("<tr>");
            stringJoiner.add(String.format("<td>%s</td>", productEntity.getId()));
            stringJoiner.add(String.format("<td>%s</td>", productEntity.getName()));
            stringJoiner.add(String.format("<td>%s</td>", productEntity.getPrice()));
            stringJoiner.add(String.format("<td>%s</td>", productEntity.getCreate().toString()));
            stringJoiner.add(String.format("<td>%s</td>", productEntity.getUpdate().toString()));
            stringJoiner.add(String.format("<td><a href=\"http://localhost:8080/products?idToDelete=%s\">Delete</a></td>", productEntity.getId()));
            stringJoiner.add(String.format("<td><a href=\"http://localhost:8080/products/update?idToUpdate=%s\">Update</a></td>", productEntity.getId()));
            stringJoiner.add("</tr>");
        }
        return stringJoiner.toString();
    }
}
