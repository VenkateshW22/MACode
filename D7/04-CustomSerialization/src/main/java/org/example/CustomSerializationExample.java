package org.example;


import java.io.*;

public class CustomSerializationExample {
    private static final String FILE_NAME = "user_profile.ser";

    public static void main(String[] args) {
        // 1. Serialization
        UserProfile user1 = new UserProfile("john_doe", "john@example.com", "mySecurePassword123");
        System.out.println("Original UserProfile: " + user1);

        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(user1);
            System.out.println("UserProfile object serialized successfully to " + FILE_NAME);
        } catch (IOException i) {
            System.out.println("IOException occurred "+ i.getMessage());
        }

        // 2. Deserialization
        UserProfile user2 = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            user2 = (UserProfile) in.readObject();
            System.out.println("UserProfile object deserialized successfully from " + FILE_NAME);
        } catch (IOException i) {
            System.out.println("IOException occurred "+ i.getMessage());
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("UserProfile class not found");
            System.out.println("IOException occurred "+ c.getMessage());
            return;
        }

        System.out.println("Deserialized UserProfile: " + user2);
        System.out.println("Username: " + user2.getUsername());
        System.out.println("Email: " + user2.getEmail());
        // Password hash should be correctly restored via custom logic
        System.out.println("Password Hash (custom handling): " + user2.getPasswordHash());
    }
}
