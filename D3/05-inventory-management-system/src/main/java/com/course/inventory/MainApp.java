// src/main/java/com/course/inventory/MainApp.java
package com.course.inventory;

import com.course.inventory.exception.DuplicateProductException;
import com.course.inventory.exception.InsufficientStockException;
import com.course.inventory.exception.ProductNotFoundException;
import com.course.inventory.model.Product;
import com.course.inventory.service.Inventory;

import java.util.Scanner;

/**
 * The main entry point for the Inventory Management System application.
 * This class is responsible for handling user input and orchestrating calls
 * to the Inventory service.
 */
public class MainApp {

    private static final Inventory inventory = new Inventory();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        setupInitialInventory();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleAddProduct();
                case "2" -> handleUpdateStock();
                case "3" -> handleCheckProduct();
                case "4" -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Inventory Menu ---");
        System.out.println("1. Add Product");
        System.out.println("2. Update Stock");
        System.out.println("3. Check Product");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }

    private static void setupInitialInventory() {
        try {
            inventory.addProduct(new Product("LAP-001", 10));
            inventory.addProduct(new Product("MSE-002", 50));
        } catch (DuplicateProductException e) {
            System.err.println("Initial data population failed: " + e.getMessage());
        }
    }

    private static void handleAddProduct() {
        try {
            System.out.print("Enter Product ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());
            inventory.addProduct(new Product(id, qty));
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format for quantity.");
        } catch (IllegalArgumentException | DuplicateProductException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void handleUpdateStock() {
        try {
            System.out.print("Enter Product ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Quantity Change (e.g., -5 or 10): ");
            int change = Integer.parseInt(scanner.nextLine());
            inventory.updateStock(id, change);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format for quantity change.");
        } catch (ProductNotFoundException | InsufficientStockException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void handleCheckProduct() {
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        inventory.getProduct(id)
                .ifPresentOrElse(
                        p -> System.out.println("Found: " + p),
                        () -> System.out.println("Product with ID '" + id + "' not found.")
                );
    }
}
