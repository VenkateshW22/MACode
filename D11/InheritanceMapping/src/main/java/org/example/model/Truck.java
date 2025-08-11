package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "trucks")
@PrimaryKeyJoinColumn(name = "truck_id")
public class Truck extends Vehicle {
    public Truck(double payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }

    public double getPayloadCapacity() {
        return payloadCapacity;
    }

    public void setPayloadCapacity(double payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }

    private double payloadCapacity;
}
