package com.mosh.course.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequest {
    @NotNull(message = "Cart id is required")
    public UUID cartId;
}
