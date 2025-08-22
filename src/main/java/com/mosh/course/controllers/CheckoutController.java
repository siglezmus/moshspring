package com.mosh.course.controllers;

import com.mosh.course.dtos.ErrorDto;
import com.mosh.course.dtos.OrderRequest;
import com.mosh.course.dtos.OrderResponse;
import com.mosh.course.exceptions.CheckoutEmptyCartException;
import com.mosh.course.exceptions.UserNotFoundException;
import com.mosh.course.services.CheckoutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
@Tag(name = "Checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<OrderResponse> newOrder(
            @Valid @RequestBody OrderRequest request
    ){
        var orderResponse = checkoutService.newOrder(request.cartId);
        return ResponseEntity.ok(orderResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("User not found"));
    }

    @ExceptionHandler(CheckoutEmptyCartException.class)
    public ResponseEntity<ErrorDto> handleCheckoutEmptyCart(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Cart cannot be empty"));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }

}
