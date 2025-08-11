package org.course;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.course.model.Product;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product product1 = new Product(100, "Laptop");
        Product product2 = new Product(200, "Tablet");
        Product product3 = new Product(300, "Phone");
        em.persist(product1);
        em.persist(product2);
        em.persist(product3);

        em.getTransaction().commit();
        System.out.println("Products are saved in the database. Commit Operations are Done");
        em.close();
        emf.close();
    }
}