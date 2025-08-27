package com.mosh.course.payments;

import com.mosh.course.carts.CartNotFoundException;
import com.mosh.course.carts.CheckoutEmptyCartException;
import com.mosh.course.common.ErrorDto;
import com.mosh.course.orders.OrderRepository;
import com.mosh.course.users.UserNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;


    @PostMapping
    public ResponseEntity<OrderResponse> newOrder(
            @Valid @RequestBody OrderRequest request
    ){
        return ResponseEntity.ok(checkoutService.newOrder(request.cartId));
    }

    @SneakyThrows
    @PostMapping("/webhook")
    public void handleWebHook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload
    ){
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("User not found"));
    }

    @ExceptionHandler(CheckoutEmptyCartException.class)
    public ResponseEntity<ErrorDto> handleCheckoutEmptyCart(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Cart cannot be empty"));
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error creating payment session"));
    }

    @ExceptionHandler(WebHookSerializationException.class)
    public ResponseEntity<ErrorDto> handleWebHookSerializationException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error creating payment session"));
    }

}
