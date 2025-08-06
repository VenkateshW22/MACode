package org.example;

import java.io.*;



public class ExternalizationExample {
    private static final String FILE_NAME = "product.dat";

    public static void main(String[] args) {
        // 1. Serialization using Externalizable
        Product product1 = new Product("P001", "Laptop", 1200.50, 5);
        System.out.println("Original Product: " + product1);

        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(product1);
            System.out.println("Product object serialized successfully to " + FILE_NAME);
        } catch (IOException i) {
            System.out.println("IOException occurred "+ i.getMessage());

        }

        // 2. Deserialization using Externalizable
        Product product2 = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            product2 = (Product) in.readObject();
            System.out.println("Product object deserialized successfully from " + FILE_NAME);
        } catch (IOException i) {
            System.out.println("IOException occurred "+ i.getMessage());
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Product class not found");
            System.out.println("IOException occurred "+ c.getMessage());
            return;
        }

        System.out.println("Deserialized Product: " + product2);
        System.out.println("Product ID: " + product2.getProductId());
        System.out.println("Name: " + product2.getName());
    }
}