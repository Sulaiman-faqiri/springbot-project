package com.practice.practice.dto;

import java.math.BigDecimal;

import com.practice.practice.model.Product;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQty,
        String description,
        Long categoryId,
        String categoryName) {

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQty(),
                product.getDescription(),
                product.getCategory().getId(),
                product.getCategory().getName());
    }
}
