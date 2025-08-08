package org.example;

import java.io.*;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 2L; // Updated serialVersionUID
    private String username;
    private String email;
    private transient String passwordHash; // Store as transient, handle serialization manually

    public UserProfile(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return "UserProfile{username='" + username + "', email='" + email + "', passwordHash='" + (passwordHash != null ? "[HIDDEN]" : "null") + "'}";
    }

    // Custom serialization logic
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serialize non-transient fields normally
        // Custom serialization for passwordHash (e.g., encrypting it before writing)
        String encryptedPassword = encrypt(passwordHash);
        out.writeUTF(encryptedPassword); // Write as UTF string
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize non-transient fields normally
        // Custom deserialization for passwordHash (e.g., decrypting it after reading)
        String encryptedPassword = in.readUTF();
        this.passwordHash = decrypt(encryptedPassword);
    }

    // Dummy encryption/decryption methods for demonstration
    private String encrypt(String data) {
        if (data == null) return null;
        return new StringBuilder(data).reverse().toString() + "_encrypted"; // Simple reverse + suffix
    }

    private String decrypt(String data) {
        if (data == null || !data.endsWith("_encrypted")) return null;
        String reversed = data.substring(0, data.length() - "_encrypted".length());
        return new StringBuilder(reversed).reverse().toString();
    }
}
