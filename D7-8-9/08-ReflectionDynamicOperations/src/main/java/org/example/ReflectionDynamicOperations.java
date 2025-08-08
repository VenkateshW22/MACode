package org.example;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionDynamicOperations {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = MyReflectionTarget.class; // Reusing MyReflectionTarget class

        // 1. Dynamic Instantiation
        System.out.println("--- Dynamic Instantiation ---");
        // Get constructor with specific parameter types
        Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
        MyReflectionTarget newObj = (MyReflectionTarget) constructor.newInstance("dynamicallyCreated", 99);
        System.out.println("Dynamically created object privateField: " + newObj.getPrivateField());
        System.out.println("Dynamically created object publicField: " + newObj.publicField);

        // 2. Dynamic Field Access
        System.out.println("\n--- Dynamic Field Access ---");
        Field publicField = clazz.getField("publicField"); // Get public field by name
        System.out.println("Initial publicField value: " + publicField.get(newObj));
        publicField.set(newObj, 200); // Set public field value
        System.out.println("Updated publicField value: " + publicField.get(newObj));

        // Accessing private fields
        Field privateField = clazz.getDeclaredField("privateField"); // Get private field by name
        privateField.setAccessible(true); // Allow access to private field (Bypasses access checks)
        System.out.println("Initial privateField value: " + privateField.get(newObj));
        privateField.set(newObj, "updatedPrivateViaReflection");
        System.out.println("Updated privateField value: " + privateField.get(newObj));
        privateField.setAccessible(false); // Good practice to revert accessibility

        // 3. Dynamic Method Invocation
        System.out.println("\n--- Dynamic Method Invocation ---");
        Method getPrivateFieldMethod = clazz.getMethod("getPrivateField"); // Get public method by name
        String result = (String) getPrivateFieldMethod.invoke(newObj); // Invoke method on object
        System.out.println("Result of getPrivateField(): " + result);

        // Invoking private methods
        Method privateMethod = clazz.getDeclaredMethod("privateMethod", String.class); // Get private method by name
        privateMethod.setAccessible(true); // Allow access to private method
        privateMethod.invoke(newObj, "Hello from Reflection!"); // Invoke private method
        privateMethod.setAccessible(false); // Revert accessibility

        // Invoking static methods
        Method staticMethod = clazz.getMethod("staticMethod");
        staticMethod.invoke(null); // Invoke static method (object instance can be null)
    }
}