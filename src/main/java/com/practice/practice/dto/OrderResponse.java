package com.practice.practice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.practice.practice.model.Order;
import com.practice.practice.model.OrderStatus;

public record OrderResponse(
        Long id,
        String orderNumber,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        Long userId,
        String userName,
        List<OrderItemResponse> items) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getItems().stream()
                        .map(OrderItemResponse::fromEntity)
                        .toList());
    }
}
