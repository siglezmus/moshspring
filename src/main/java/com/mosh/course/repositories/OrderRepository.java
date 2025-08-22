package com.mosh.course.repositories;

import com.mosh.course.models.Cart;
import com.mosh.course.models.Order;
import com.mosh.course.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {
            "items",
            "items.product",
            "items.product.category"
    })
    @Query("SELECT o FROM Order o")  // Optional if you're doing findAll
    List<Order> findAllEager();

    @EntityGraph(attributePaths = {
            "items",
            "items.product",
            "items.product.category"
    })
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findByIdEager(@Param("id") Long id);

    @EntityGraph(attributePaths = {
            "items",
            "items.product",
            "items.product.category"
    })
    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
    List<Order> findAllByCustomerIdEager(Long customerId);
}
