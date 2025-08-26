package com.mosh.course.payments;

import com.mosh.course.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentStatus {
    private Long orderId;
    private OrderStatus orderStatus;
}
