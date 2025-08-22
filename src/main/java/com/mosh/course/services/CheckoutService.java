package com.mosh.course.services;

import com.mosh.course.dtos.ErrorDto;
import com.mosh.course.dtos.OrderResponse;
import com.mosh.course.exceptions.CartNotFoundException;
import com.mosh.course.exceptions.CheckoutEmptyCartException;
import com.mosh.course.exceptions.UserNotFoundException;
import com.mosh.course.mappers.OrderMapper;
import com.mosh.course.models.Order;
import com.mosh.course.repositories.CartRepository;
import com.mosh.course.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final AuthService authService;

    public OrderResponse newOrder(UUID carId){
        var user = authService.getCurrentUser();
        if (user == null) throw new UserNotFoundException();

        Long userId = user.getId();

        var cart = cartRepository.findByIdEager(carId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        if (cart.getItems().isEmpty()) throw new CheckoutEmptyCartException();


        var order = new Order(cart, userId);
        cartService.clearCart(cart.getId());

        orderRepository.save(order);
        return orderMapper.orderToDto(order);
    }
}
