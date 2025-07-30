// src/main/java/com/course/inventory/exception/DuplicateProductException.java
package com.course.inventory.exception;

/**
 * A checked exception thrown when attempting to add a product that already exists.
 */
public class DuplicateProductException extends Exception {
    public DuplicateProductException(String message) {
        super(message);
    }
}
