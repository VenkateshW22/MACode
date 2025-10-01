package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.ProductDto;
import com.ecommerce.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.orderservice.model.Order;
import java.time.LocalDateTime;
import jakarta.persistence.EntityNotFoundException;


@Service
public class OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        // 1. Call Product service to check for product existence and stock
        ProductDto product = productClient.getProductById(order.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found or Product Service is down"));

        if (product.stockQuantity() < order.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.name());
        }

        // We no longer need ProductRepository here.
        // The logic to update stock will move to the product-service itself later (in the Saga module)
        // For now, this call is just for validation.

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");
        return orderRepository.save(order);
    }
}