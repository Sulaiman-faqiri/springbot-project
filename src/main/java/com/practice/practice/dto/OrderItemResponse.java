package com.practice.practice.dto;

import java.math.BigDecimal;

import com.practice.practice.model.OrderItem;

public record OrderItemResponse(
        Long id,
        Long productId,
        String productName,
        Integer qty,
        BigDecimal unitPrice,
        BigDecimal lineTotal) {

    public static OrderItemResponse fromEntity(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQty(),
                item.getUnitPrice(),
                item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQty())));
    }
}
