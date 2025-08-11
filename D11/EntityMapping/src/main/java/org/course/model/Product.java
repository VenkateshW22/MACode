package org.course.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;
    @Column(name = "product_name", nullable = false, length = 100)
    private String name;
    @Column(name = "product_price", nullable = false)
    private int price;
    @Transient
    private String temporaryField;

    public Product() {
    }

    public Product(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getTemporaryField() {
        return temporaryField;
    }
}
