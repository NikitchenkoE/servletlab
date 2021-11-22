package com.mapper;

import com.dto.ProductDto;
import com.entity.Product;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MapToProductDto {
    public ProductDto mapToDto(Product product) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withLocale(Locale.ENGLISH)
                .withZone(ZoneId.of("UTC"));
        String formattedCreate = dateTimeFormatter.format(product.getCreate().toInstant());
        String formattedUpdate = dateTimeFormatter.format(product.getUpdate().toInstant());

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .create(formattedCreate)
                .update(formattedUpdate)
                .build();
    }
}
