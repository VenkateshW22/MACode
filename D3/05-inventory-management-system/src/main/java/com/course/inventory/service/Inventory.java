// src/main/java/com/course/inventory/service/Inventory.java
package com.course.inventory.service;

import com.course.inventory.exception.DuplicateProductException;
import com.course.inventory.exception.InsufficientStockException;
import com.course.inventory.exception.ProductNotFoundException;
import com.course.inventory.model.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages all inventory operations, such as adding products and updating stock.
 * This class contains the core business logic.
 */
public class Inventory {
    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) throws DuplicateProductException {
        if (products.containsKey(product.getProductId())) {
            throw new DuplicateProductException("Error: Product with ID '" + product.getProductId() + "' already exists.");
        }
        products.put(product.getProductId(), product);
        System.out.println("Product added: " + product);
    }

    public Optional<Product> getProduct(String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    public void updateStock(String productId, int quantityChange) throws InsufficientStockException {
        Product product = getProduct(productId)
                .orElseThrow(() -> new ProductNotFoundException("Error: Product with ID '" + productId + "' not found."));

        int newQuantity = product.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new InsufficientStockException("Error: Cannot remove " + (-quantityChange) + " items. Only " + product.getQuantity() + " available.");
        }
        product.setQuantity(newQuantity);
        System.out.println("Stock updated: " + product);
    }
}
