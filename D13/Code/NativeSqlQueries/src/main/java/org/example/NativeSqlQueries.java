package org.example;

import org.example.model.Product;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class NativeSqlQueries {

    public static void main(String[] args) {
        System.out.println("--- 1. Basic Native SQL Query ---");
        basicNativeSqlQuery();

        System.out.println("\n--- 2. Native SQL with Scalar Values ---");
        scalarNativeSqlQuery();

        System.out.println("\n--- 3. Native SQL with LIKE ---");
        findProductsByNameNativeSql();
    }

    private static void basicNativeSqlQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Native SQL query to select all products
            String sql = "SELECT * FROM products";

            // Create a NativeQuery and specify the entity to map the results to
            NativeQuery<Product> query = session.createNativeQuery(sql, Product.class);

            List<Product> products = query.getResultList();

            System.out.println("All Products (Native SQL):");
            products.forEach(System.out::println);
        }
    }

    private static void scalarNativeSqlQuery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Native SQL query to get name and price, which are scalar values
            String sql = "SELECT name, price FROM products WHERE price > :minPrice";

            // We don't map to an entity here, so we get a list of Object arrays
            NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);

            // Bind the named parameter
            query.setParameter("minPrice", 200.0);

            List<Object[]> results = query.getResultList();

            System.out.println("Product names and prices for products over $200:");
            for (Object[] result : results) {
                System.out.println("Name: " + result[0] + ", Price: " + result[1]);
            }
        }

    }
    private static void findProductsByNameNativeSql() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM products WHERE name LIKE :searchName";
            NativeQuery<Product> query = session.createNativeQuery(sql, Product.class);
            query.setParameter("searchName", "%mouse%");

            List<Product> products = query.getResultList();
            System.out.println("Products with 'mouse' in name (Native SQL):");
            products.forEach(System.out::println);
        }
    }

}