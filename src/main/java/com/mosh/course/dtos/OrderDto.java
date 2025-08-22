package com.mosh.course.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private String orderStatus;
    private OffsetDateTime createdAt;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

}
