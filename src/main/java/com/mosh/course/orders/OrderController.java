package com.mosh.course.orders;

import com.mosh.course.dtos.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
