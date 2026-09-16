package com.practice.practice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductRequest {
    @NotNull(message = "category is required")
    private Long categoryId;
    @NotBlank(message = "name is required")
    private String name;
    @NotNull
    @PositiveOrZero(message = "price must be positive")
    private BigDecimal price;
    @NotNull
    @PositiveOrZero(message = "stock must be positive")
    private Integer stockQty;
    private String description;
}
