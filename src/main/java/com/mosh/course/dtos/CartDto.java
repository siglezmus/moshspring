package com.mosh.course.dtos;

import com.mosh.course.models.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private HashSet<CartItem> items;
    private BigDecimal totalCartPrice;
}
