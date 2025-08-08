// src/main/java/com/myuniversity/model/Student.java (for IDENTITY)
package com.myuniversity.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Uses database AUTO_INCREMENT
    private Long id;
    private String name;
    private String email;

    public Student() {}
    public Student(String name, String email) { this.name = name; this.email = email; }
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) {  this.email = email; }
}