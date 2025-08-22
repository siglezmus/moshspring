package com.mosh.course.services;

import com.mosh.course.dtos.OrderDto;
import com.mosh.course.exceptions.OrderNotFound;
import com.mosh.course.mappers.OrderMapper;
import com.mosh.course.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
