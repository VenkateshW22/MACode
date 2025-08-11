package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
@PrimaryKeyJoinColumn(name = "car_id") // Links this table to the primary key of the Vehicle table
public class Car extends Vehicle {
    private int numDoors;

    public Car() {
        // Default constructor required by JPA
    }

    public Car(String manufacturer, int numDoors) {
        this.manufacturer = manufacturer;
        this.numDoors = numDoors;
    }

    public int getNumDoors() {
        return numDoors;
    }

    public void setNumDoors(int numDoors) {
        this.numDoors = numDoors;
    }

    public Car(int numDoors) {
        this.numDoors = numDoors;
    }
}
