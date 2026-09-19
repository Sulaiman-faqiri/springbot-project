package com.practice.practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.practice.dto.OrderResponse;
import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.Order;
import com.practice.practice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        return OrderResponse.fromEntity(findOrder(id));
    }

    @Transactional
    public void delete(Long id) {
        orderRepository.delete(findOrder(id));
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("order", id));
    }

}
