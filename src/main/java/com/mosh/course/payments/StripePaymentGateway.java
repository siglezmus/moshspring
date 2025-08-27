package com.mosh.course.payments;

import com.mosh.course.orders.Order;
import com.mosh.course.orders.OrderItem;
import com.mosh.course.orders.OrderStatus;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StripePaymentGateway implements PaymentGateway{


    @Value("${websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try{
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel.html")
                    .setPaymentIntentData(getPaymentIntentData(order));

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        } catch (StripeException ex){
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }

    private static SessionCreateParams.PaymentIntentData getPaymentIntentData(Order order) {
        return SessionCreateParams.PaymentIntentData.builder().
                putMetadata("order_id", order.getId().toString()).build();
    }

    @Override
    public Optional<PaymentStatus> parseWebhookRequest(WebhookRequest request) {
        try {
            var event = Webhook.constructEvent(request.getPayload(), request.getHeaders().get("stripe-signature"), webhookSecretKey);

            var deserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = deserializer.getObject()
                    .orElseGet(() -> {
                        try {
                            return deserializer.deserializeUnsafe();
                        } catch (EventDataObjectDeserializationException e) {
                            throw new WebHookSerializationException();
                        }
                    });

            OrderStatus status;
            if (stripeObject instanceof PaymentIntent paymentIntent) {
                return switch (event.getType()) {
                    case "payment_intent.succeeded" ->
                        Optional.of(new PaymentStatus(extractOrderId(paymentIntent), OrderStatus.PAID));
                    case "payment_intent.failed" ->
                        Optional.of(new PaymentStatus(extractOrderId(paymentIntent), OrderStatus.FAILED));
                    default -> Optional.empty();
                };
            }
            return Optional.empty();
        } catch (SignatureVerificationException ex) {
            return Optional.empty();
        }
    }

    private static Long extractOrderId(PaymentIntent paymentIntent){
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

    private static SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
