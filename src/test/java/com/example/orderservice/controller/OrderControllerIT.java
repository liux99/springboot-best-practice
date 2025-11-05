package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateOrder() {
        OrderRequest req = new OrderRequest();
        req.setCustomerName("IntegrationTest");
        req.setAmount(99.99);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/orders", req, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}