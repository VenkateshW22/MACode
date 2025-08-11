package org.example;

import org.example.model.Car;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Car car = new Car("Honda", 4);
        em.persist(car);

        em.getTransaction().commit();
        System.out.println("Car saved with ID: " + car.getId());
        em.close();
        emf.close();
    }
}