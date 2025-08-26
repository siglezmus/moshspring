package com.mosh.course.carts;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private HashSet<CartItem> items;
    private BigDecimal totalCartPrice;
}
