package com.example.payment.entity;

import com.example.dto.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer orderId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
