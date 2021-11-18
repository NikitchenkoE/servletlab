package com.maper;

import com.dto.ProductDto;
import com.entity.Product;
import freemarker.core.JSONOutputFormat;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class MapToProductDtoTest {
    MapToProductDto mapToProductDto = new MapToProductDto();
    @Test
    void testMapToProductDto() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        String dateInString = "22-01-2015 10:15:55 AM";
        Date date = formatter.parse(dateInString);
        Product productEntity1 = new Product(1L, "first", 15.0, date, date);
        ProductDto productDto = mapToProductDto.mapToDto(productEntity1);
        String expectedDate = "2015-01-22 15:15:55";
        String actualCreated = productDto.getCreate();
        String actualUpdated = productDto.getUpdate();
        assertEquals(expectedDate,actualCreated);
        assertEquals(expectedDate,actualUpdated);
    }
}