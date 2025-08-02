package com.mosh.course.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateItemInCartRequest {
    @Min(1)
    @Max(99)
    private Integer quantity;
}