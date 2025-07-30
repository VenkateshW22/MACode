// src/main/java/com/course/inventory/exception/InsufficientStockException.java
package com.course.inventory.exception;

/**
 * A checked exception thrown when a stock removal operation cannot be completed
 * due to a lack of available items.
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}
