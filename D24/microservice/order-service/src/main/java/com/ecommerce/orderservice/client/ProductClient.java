package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "product-service", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService")
    Optional<ProductDto> getProductById(@PathVariable Long id);

    @Component
    class ProductClientFallback implements ProductClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(ProductClientFallback.class);

        @Override
        public Optional<ProductDto> getProductById(Long id) {
            LOGGER.error("Fallback for getProductById: Product service is down. productId={}", id);
            return Optional.empty();
        }
    }
}