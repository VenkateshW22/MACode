package org.example;

import org.example.model.Product;
import org.example.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CriteriaQueries {

    public static void main(String[] args) {
        System.out.println("--- 1. Basic Criteria Query ---");
        basicCriteriaQuery();

        System.out.println("\n--- 2. Criteria Query with Predicate ---");
        queryWithPredicate();

        System.out.println("\n--- 3. Find Products by Stock and Price ---");
        findProductsByStockAndPrice();
    }

    private static void basicCriteriaQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Get the CriteriaBuilder from the session
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Create a CriteriaQuery for our entity type
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);

            // Define the FROM clause: the root of the query
            Root<Product> root = cq.from(Product.class);

            // The SELECT part is implicitly handled by the CriteriaQuery's type
            cq.select(root);

            // Create the query object and get the results
            Query<Product> query = session.createQuery(cq);
            List<Product> products = query.getResultList();

            System.out.println("All Products (Criteria API):");
            products.forEach(System.out::println);
        }
    }

    private static void queryWithPredicate() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            // Create a predicate for the WHERE clause: price > 100
            // Note the type-safe access to the 'price' property
            jakarta.persistence.criteria.Predicate pricePredicate = cb.gt(root.get("price"), 100.0);

            // Add the WHERE clause to the query
            cq.where(pricePredicate);

            // Add an ORDER BY clause
            cq.orderBy(cb.desc(root.get("price")));

            Query<Product> query = session.createQuery(cq);
            List<Product> products = query.getResultList();

            System.out.println("Expensive Products (Criteria API):");
            products.forEach(System.out::println);
        }
    }

    private static void findProductsByStockAndPrice() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            // Create predicates for both conditions
            jakarta.persistence.criteria.Predicate stockPredicate = cb.lessThan(root.get("stock"), 100);
            jakarta.persistence.criteria.Predicate pricePredicate = cb.greaterThan(root.get("price"), 50.0);

            // Combine the predicates with an AND operator
            cq.where(cb.and(stockPredicate, pricePredicate));

            Query<Product> query = session.createQuery(cq);
            List<Product> products = query.getResultList();

            System.out.println("Products with stock < 100 AND price > $50:");
            products.forEach(System.out::println);
        }
    }
}