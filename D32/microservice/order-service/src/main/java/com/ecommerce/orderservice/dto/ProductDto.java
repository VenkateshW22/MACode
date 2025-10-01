package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String description,
        double price,
        int stockQuantity
) {}
