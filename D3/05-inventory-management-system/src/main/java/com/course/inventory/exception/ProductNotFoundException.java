// src/main/java/com/course/inventory/exception/ProductNotFoundException.java
package com.course.inventory.exception;

/**
 * An unchecked exception thrown when an operation is attempted on a product ID
 * that does not exist in the inventory.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
