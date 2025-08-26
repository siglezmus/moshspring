package com.mosh.course.payments;

import com.mosh.course.orders.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentStatus> parseWebhookRequest(WebhookRequest request);
}
