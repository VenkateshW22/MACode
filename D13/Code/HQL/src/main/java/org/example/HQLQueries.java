package org.example;

import org.example.model.Product;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HQLQueries {

    public static void main(String[] args) {
        // First, let's add some data to query
        insertInitialData();

        System.out.println("--- 1. Basic SELECT Query ---");
        basicSelectQuery();

        System.out.println("\n--- 2. SELECT with WHERE and ORDER BY ---");
        whereAndOrderByQuery();

        System.out.println("\n--- 3. Using Named Parameters ---");
        namedParameterQuery();

        System.out.println("\n--- 4. Using Positional Parameters ---");
        positionalParameterQuery();
    }

    private static void insertInitialData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(new Product("Laptop", 1200.0, 50));
            session.persist(new Product("Mouse", 25.50, 200));
            session.persist(new Product("Keyboard", 75.0, 150));
            session.persist(new Product("Monitor", 300.0, 80));
            session.persist(new Product("Webcam", 50.0, 120));
            session.getTransaction().commit();
            System.out.println("Initial data inserted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void basicSelectQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: selects all Product objects
            String hql = "FROM Product";

            // Create a query object and specify the result type
            Query<Product> query = session.createQuery(hql, Product.class);

            // Execute the query and get the list of results
            List<Product> products = query.list();

            System.out.println("All Products:");
            products.forEach(System.out::println);
        }
    }

    private static void whereAndOrderByQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: select products with price > 100, ordered by price descending
            String hql = "FROM Product p WHERE p.price > 100 ORDER BY p.price DESC";

            Query<Product> query = session.createQuery(hql, Product.class);
            List<Product> expensiveProducts = query.list();

            System.out.println("Expensive Products (Price > $100), sorted by price:");
            expensiveProducts.forEach(System.out::println);
        }
    }

    private static void namedParameterQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: using a named parameter `:minPrice`
            String hql = "FROM Product p WHERE p.price > :minPrice";

            Query<Product> query = session.createQuery(hql, Product.class);

            // Bind the value to the named parameter
            query.setParameter("minPrice", 200.0);

            List<Product> results = query.list();
            System.out.println("Products with price > $200 (using named parameter):");
            results.forEach(System.out::println);
        }
    }

    private static void positionalParameterQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: using a positional parameter `?0`
            String hql = "FROM Product p WHERE p.stock < ?1";

            Query<Product> query = session.createQuery(hql, Product.class);

            // Bind the value to the positional parameter
            query.setParameter(1, 100);

            List<Product> results = query.list();
            System.out.println("Products with stock < 100 (using positional parameter):");
            results.forEach(System.out::println);
        }
    }
}
