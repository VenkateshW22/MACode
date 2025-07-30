// src/main/java/com/course/inventory/model/Product.java
package com.course.inventory.model;

/**
 * Represents a product in the inventory with an ID and quantity.
 * Enforces validation rules in its constructor.
 */
public class Product {
    private final String productId;
    private int quantity;

    public Product(String productId, int quantity) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Product[ID='" + productId + "', Quantity=" + quantity + "]";
    }
}
