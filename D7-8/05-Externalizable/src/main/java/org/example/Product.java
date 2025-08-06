package org.example;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Product implements Externalizable {
    private String productId;
    private String name;
    private double price;
    private int quantity;

    // A public no-arg constructor is REQUIRED for Externalizable
    public Product() {
        System.out.println("Product: No-arg constructor called.");
    }

    public Product(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Product: writeExternal called.");
        out.writeUTF(productId);
        out.writeUTF(name);
        out.writeDouble(price);
        out.writeInt(quantity);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Product: readExternal called.");
        this.productId = in.readUTF();
        this.name = in.readUTF();
        this.price = in.readDouble();
        this.quantity = in.readInt();
    }
}