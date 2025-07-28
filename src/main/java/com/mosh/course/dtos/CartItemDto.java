package com.mosh.course.dtos;

import com.mosh.course.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
