package com.mosh.course.repositories;

import com.mosh.course.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("""
        SELECT DISTINCT c FROM Cart c
        LEFT JOIN FETCH c.items ci
        LEFT JOIN FETCH ci.product p
        LEFT JOIN FETCH p.category
    """)
    List<Cart> findAllEager();

    @Query("""
    SELECT c FROM Cart c
    LEFT JOIN FETCH c.items ci
    LEFT JOIN FETCH ci.product p
    LEFT JOIN FETCH p.category
    WHERE c.id = :id
""")
    Optional<Cart> findByIdEager(UUID id);
}
