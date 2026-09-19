package com.practice.practice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.practice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "user")
    Page<Order> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "user", "items", "items.product" })
    Optional<Order> findById(Long id);
}
