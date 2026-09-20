package com.practice.practice.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.practice.dto.OrderItemRequest;
import com.practice.practice.dto.OrderRequest;
import com.practice.practice.dto.OrderResponse;
import com.practice.practice.exception.ApiException;
import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.Order;
import com.practice.practice.model.OrderItem;
import com.practice.practice.model.OrderStatus;
import com.practice.practice.model.Product;
import com.practice.practice.model.User;
import com.practice.practice.repository.OrderRepository;
import com.practice.practice.repository.ProductRepository;
import com.practice.practice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderResponse::fromEntity);
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", request.getUserId()));
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderNumber("Order-" + System.currentTimeMillis());
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest orderItem : request.getItems()) {

            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("product", orderItem.getProductId()));
            if (orderItem.getQty() > product.getStockQty()) {
                throw new ApiException(HttpStatus.CONFLICT, "not enough stock for " + product.getName());

            }
            product.setStockQty(product.getStockQty() - orderItem.getQty());
            OrderItem oItem = new OrderItem();
            oItem.setProduct(product);
            oItem.setQty(orderItem.getQty());
            oItem.setUnitPrice(product.getPrice());
            order.addItem(oItem);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQty())));
        }
        order.setTotalAmount(total);
        orderRepository.save(order);
        return OrderResponse.fromEntity(order);

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
