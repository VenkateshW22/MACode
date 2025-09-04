package com.example.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class UserBalance {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Double balance;
}
