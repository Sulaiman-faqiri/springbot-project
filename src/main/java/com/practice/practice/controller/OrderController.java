package com.practice.practice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.dto.OrderRequest;
import com.practice.practice.dto.OrderResponse;
import com.practice.practice.dto.OrderStatusRequest;
import com.practice.practice.model.OrderStatus;
import com.practice.practice.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Page<OrderResponse> index(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return orderService.getAllOrders(userId, status, pageable);
    }

    @GetMapping("/{id}")
    public OrderResponse show(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PatchMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusRequest request) {
        return orderService.updateStatus(id, request);

    }

    @PatchMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id, @RequestBody @Valid OrderStatusRequest request) {
        return orderService.cancelOrder(id, request);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
