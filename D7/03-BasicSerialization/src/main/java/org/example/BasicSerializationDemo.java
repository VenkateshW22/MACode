package org.example;

import org.example.model.Student;

import java.io.*;

public class BasicSerializationDemo {

    private static final String FILE_NAME = "student.ser";

    public static void main(String[] args) {
        // 1. Serialization
        Student student1 = new Student("Alice", 25, "Top Secret Code 123");
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(student1);
            System.out.println("Student object serialized successfully to " + FILE_NAME);
        } catch (IOException i) {
            System.out.println("IOException occurred "+ i.getMessage());
        }

        // 2. Deserialization
        Student student2 = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            student2 = (Student) in.readObject();
            System.out.println("Student object deserialized successfully from " + FILE_NAME);
        } catch (IOException i) {
            System.out.println("IOException occurred "+ i.getMessage());
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Student class not found");
            System.out.println("ClassNotFoundException occurred "+c.getMessage());
            return;
        }

        System.out.println("Deserialized Student: " + student2);
        System.out.println("Name: " + student2.getName());
        System.out.println("Age: " + student2.getAge());
        // Note: secretInfo will be null or default value upon deserialization because it's transient
        System.out.println("Secret Info (transient): " + student2.getSecretInfo());
    }
}