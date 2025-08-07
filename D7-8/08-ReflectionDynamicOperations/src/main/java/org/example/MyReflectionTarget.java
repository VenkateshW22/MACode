package org.example;

public class MyReflectionTarget {
    private String privateField;
    public int publicField;

    public MyReflectionTarget(String privateField, int publicField) {
        this.privateField = privateField;
        this.publicField = publicField;
    }

    public String getPrivateField() {
        return privateField;
    }

    private void privateMethod(String message) {
        System.out.println("Private method called with: " + message);
    }

    public static void staticMethod() {
        System.out.println("Static method called.");
    }
}
