package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository repository;

    public Order createOrder(OrderRequest request) {
    	log.info("Creating order for customer: {}", request.getCustomerName());
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .amount(request.getAmount())
                .createdAt(LocalDateTime.now())
                .build();
        return repository.save(order);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}