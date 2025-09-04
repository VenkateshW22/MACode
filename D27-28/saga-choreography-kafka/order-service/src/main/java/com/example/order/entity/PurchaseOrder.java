package com.example.order.entity;

import com.example.dto.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "purchase_order")
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Double price;
    @Enumerated
    private OrderStatus status;
}
