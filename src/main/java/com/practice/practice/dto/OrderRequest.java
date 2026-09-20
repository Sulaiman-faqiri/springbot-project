package com.practice.practice.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class OrderRequest {
    @NotNull
    Long userId;
    @NotEmpty @Valid List<OrderItemRequest>items;
}
