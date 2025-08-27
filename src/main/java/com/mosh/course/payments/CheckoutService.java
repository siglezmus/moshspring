package com.mosh.course.payments;

import com.mosh.course.carts.CartNotFoundException;
import com.mosh.course.carts.CheckoutEmptyCartException;
import com.mosh.course.orders.Order;
import com.mosh.course.carts.CartRepository;
import com.mosh.course.orders.OrderRepository;
import com.mosh.course.auth.AuthService;
import com.mosh.course.carts.CartService;
import com.mosh.course.users.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {


    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public OrderResponse newOrder(UUID cartId) throws PaymentException {
        var user = authService.getCurrentUser();
        if (user == null) throw new UserNotFoundException();

        Long userId = user.getId();

        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        if (cart.getItems().isEmpty()) throw new CheckoutEmptyCartException();


        var order = new Order(cart, userId);
        orderRepository.save(order);

        // Create a checkout session

        try{

            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());

            return new OrderResponse(order.getId(), session.getCheckoutUrl());
        }
        catch (PaymentException ex){
            System.out.println(ex.getMessage());
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway.parseWebhookRequest(request).ifPresent(paymentStatus -> {
            if (paymentStatus.getOrderId() != null) {
                var order = orderRepository.findById(paymentStatus.getOrderId()).orElseThrow();
                order.setOrderStatus(paymentStatus.getOrderStatus());
                orderRepository.save(order);
            }
        });
    }
}
