// Simplified SessionFactory creation (common in standalone apps)
// In modern frameworks like Spring, this is often auto-configured.
package com.myuniversity.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    // Static block to ensure SessionFactory is built once when the class is loaded
    static {
        try {
            // Configuration loads hibernate.cfg.xml from classpath
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
