package com.practice.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.practice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
