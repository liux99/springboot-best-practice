package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Test
    public void testCreateOrder() {
        OrderRepository mockRepo = mock(OrderRepository.class);
        OrderService service = new OrderService();
        OrderRequest req = new OrderRequest();
        req.setCustomerName("John");
        req.setAmount(123.45);
        when(mockRepo.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        Order result = service.createOrder(req);
        assertEquals("John", result.getCustomerName());
    }
}