package com.practice.practice.service;

import org.springframework.stereotype.Service;

import com.practice.practice.repository.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

}
