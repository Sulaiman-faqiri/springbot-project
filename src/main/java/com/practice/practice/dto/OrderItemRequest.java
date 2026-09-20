package com.practice.practice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data 
public class OrderItemRequest {
    @NotNull Long productId;
    @NotNull @Positive  Integer qty;
}
