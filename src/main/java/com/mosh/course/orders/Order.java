package com.mosh.course.orders;


import com.mosh.course.carts.Cart;
import com.mosh.course.carts.CartItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    public Order (Cart cart, Long customerId){
        this.customerId = customerId;
        totalPrice = cart.getTotalPrice();
        orderStatus = OrderStatus.PENDING;
        createdAt = OffsetDateTime.now(java.time.ZoneOffset.UTC);
        cart.getItems().forEach(this::addNewItem);
    }

    public OrderItem getItem(Long productId){
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void addNewItem(CartItem cartItem){
        var orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setOrder(this);
        orderItem.setUnitPrice(cartItem.getProduct().getPrice());
        orderItem.setTotalPrice(cartItem.getTotalPrice());
        items.add(orderItem);

    };

    public void removeItem(Long productId){
        OrderItem orderItem = getItem(productId);
        if(orderItem != null) {
            items.remove(orderItem);
            orderItem.setOrder(null);
        }
    };

    public void updateTotalPrice(){
        totalPrice = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear(){
        items.clear();
    }
}
