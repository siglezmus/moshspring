package com.mosh.course.repositories;

import com.mosh.course.models.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = {
            "items",
            "items.product",
            "items.product.category"
    })
    @Query("SELECT c FROM Cart c")  // Optional if you're doing findAll
    List<Cart> findAllEager();

    @EntityGraph(attributePaths = {
            "items",
            "items.product",
            "items.product.category"
    })
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Optional<Cart> findByIdEager(@Param("id") UUID id);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteAllByCartId(@Param("cartId") UUID cartId);
}
