package org.example;

import org.example.model.Address;
import org.example.model.Order;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Address billing = new Address("101 Main St", "Anytown", "12345");
        Address shipping = new Address("102 Side St", "Othertown", "67890");

        Order order = new Order();
        order.setBillingAddress(billing);
        order.setShippingAddress(shipping);
        em.persist(order);

        em.getTransaction().commit();
        System.out.println("Order saved with ID: " + order.getId());
        em.close();
        emf.close();
    }
}
