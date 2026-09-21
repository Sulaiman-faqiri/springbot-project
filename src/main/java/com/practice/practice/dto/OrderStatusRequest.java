package com.practice.practice.dto;

import com.practice.practice.model.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class OrderStatusRequest {
    @NotNull private OrderStatus status;
}
