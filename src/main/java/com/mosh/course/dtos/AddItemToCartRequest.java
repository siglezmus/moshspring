package com.mosh.course.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddItemToCartRequest {
    private Long productId;
}
