package com.mosh.course.orders;

import com.mosh.course.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllCustomerOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.findAllByCustomerIdEager(user.getId());
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getCustomerOrder(Long orderId){
        var userId = authService.getCurrentUserId();
        var order = orderRepository.findByIdEager(orderId).orElseThrow(OrderNotFound::new);
        if (!order.getCustomerId().equals(userId)) throw new AccessDeniedException("Current user has no access to this order");

        return orderMapper.toDto(order);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
            if (orderId != null) {
                var order = orderRepository.findById(orderId).orElseThrow();
                order.setOrderStatus(status);
                orderRepository.save(order);
            }
    }

}
