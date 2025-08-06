package org.example.model;

import java.io.*;

// Student class must implement Serializable to be written to a file
public class Student implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for versioning
    private String name;
    private int age;
    private transient String secretInfo; // This field will not be serialized

    public Student(String name, int age, String secretInfo) {
        this.name = name;
        this.age = age;
        this.secretInfo = secretInfo;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSecretInfo() {
        return secretInfo;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", secretInfo='" + secretInfo + "'}";
    }
}
