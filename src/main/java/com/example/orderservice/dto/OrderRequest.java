package com.example.orderservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class OrderRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.1", message = "Amount must be at least 0.1")
    private Double amount;
}