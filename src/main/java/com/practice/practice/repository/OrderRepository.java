package com.practice.practice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practice.practice.model.Order;
import com.practice.practice.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "user")
    Page<Order> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "user", "items", "items.product" })
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = "user")
    @Query("""
            SELECT o FROM Order o
            WHERE (:userId IS NULL OR o.user.id = :userId)
            AND (:status IS NULL OR o.status = :status)
            """)
    Page<Order> search(
            @Param("userId") Long userId,
            @Param("status") OrderStatus status,
            Pageable pageable);
}
