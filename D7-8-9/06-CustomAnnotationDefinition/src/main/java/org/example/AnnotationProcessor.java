package org.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationProcessor {

    public static void main(String[] args) {
        // Process annotations on the class
        processClassAnnotations(CustomAnnotation.UserService.class);

        // Process annotations on methods
        processMethodAnnotations(CustomAnnotation.UserService.class);
    }

    private static void processClassAnnotations(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ServiceInfo.class)) {
            ServiceInfo serviceInfo = clazz.getAnnotation(ServiceInfo.class);
            System.out.println("\n--- Processing Class Annotation: @ServiceInfo ---");
            System.out.println("Service Name: " + serviceInfo.name());
            System.out.println("Service Version: " + serviceInfo.version());
            System.out.print("Authors: ");
            for (String author : serviceInfo.authors()) {
                System.out.print(author + " ");
            }
            System.out.println();
        }
    }

    private static void processMethodAnnotations(Class<?> clazz) {
        System.out.println("\n--- Processing Method Annotations: @Loggable ---");
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Loggable.class)) {
                Loggable loggable = method.getAnnotation(Loggable.class);
                System.out.println("Method: " + method.getName());
                System.out.println("  Log Message: " + loggable.value());
                System.out.println("  Logging Enabled: " + loggable.enabled());

                if (loggable.enabled()) {
                    // Simulate logging based on annotation properties
                    System.out.println("  [Simulating Log] " + loggable.value() + " for method " + method.getName());
                } else {
                    System.out.println("  Logging is disabled for this method.");
                }
            }
        }
    }
}