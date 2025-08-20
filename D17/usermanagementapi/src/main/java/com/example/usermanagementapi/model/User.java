package com.example.usermanagementapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 50, message = "name must be at least 2 characters within 50 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;
}
