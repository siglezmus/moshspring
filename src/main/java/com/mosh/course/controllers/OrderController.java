package com.mosh.course.controllers;

import com.mosh.course.dtos.ErrorDto;
import com.mosh.course.dtos.OrderDto;
import com.mosh.course.exceptions.CheckoutEmptyCartException;
import com.mosh.course.exceptions.OrderNotFound;
import com.mosh.course.exceptions.UserNotFoundException;
import com.mosh.course.mappers.OrderMapper;
import com.mosh.course.repositories.OrderRepository;
import com.mosh.course.services.AuthService;
import com.mosh.course.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllCustomerOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getCustomerOrder(orderId));
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<ErrorDto> handleOrderNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Order not found"));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("Current user has no access to this order"));
    }
}
