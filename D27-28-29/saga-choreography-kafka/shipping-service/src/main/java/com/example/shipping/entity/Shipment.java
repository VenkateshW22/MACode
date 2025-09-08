package com.example.shipping.entity;

import com.example.dto.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Shipment {
    @Id
    @GeneratedValue
    private Integer id;
    private int orderId;
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;
}
