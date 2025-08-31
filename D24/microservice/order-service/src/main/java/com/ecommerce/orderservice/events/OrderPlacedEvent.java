package com.ecommerce.orderservice.events;

// Event payload
public record OrderPlacedEvent(
        Long orderId,
        Long userId,
        Long productId,
        int quantity
) {}
