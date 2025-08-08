package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.lang.reflect.*; // Import all reflection classes
import org.example.MyReflectionTarget;


public class ReflectionBasics {

    public static void main(String[] args) throws ClassNotFoundException {
        // 1. Getting Class objects
        System.out.println("--- Obtaining Class Objects ---");
        Class<?> class1 = MyReflectionTarget.class;
        System.out.println("Using .class literal: " + class1.getName());


        MyReflectionTarget obj = new MyReflectionTarget("initialPrivate", 10);
        Class<?> class3 = obj.getClass();
        System.out.println("Using object.getClass(): " + class3.getName());

        // Demonstrate primitive type Class objects
        Class<?> intClass = int.class;
        System.out.println("Primitive int class: " + intClass.getName());

        // 2. Inspecting Class members
        System.out.println("\n--- Inspecting Class Members ---");

        // Get all public fields (including inherited ones)
        System.out.println("\nPublic Fields:");
        for (Field field : class1.getFields()) {
            System.out.println("  " + field.getName() + " (Type: " + field.getType().getName() + ")");
        }

        // Get all declared fields (private, public, protected, default - but not inherited)
        System.out.println("\nDeclared Fields (all access modifiers):");
        for (Field field : class1.getDeclaredFields()) {
            System.out.println("  " + field.getName() + " (Type: " + field.getType().getName() + ")");
        }

        // Get all public methods (including inherited ones)
        System.out.println("\nPublic Methods:");
        for (Method method : class1.getMethods()) {
            System.out.println("  " + method.getName() + " (Return: " + method.getReturnType().getName() + ")");
        }

        // Get all declared methods (private, public, protected, default - but not inherited)
        System.out.println("\nDeclared Methods (all access modifiers):");
        for (Method method : class1.getDeclaredMethods()) {
            System.out.println("  " + method.getName() + " (Return: " + method.getReturnType().getName() + ")");
        }

        // Get all public constructors
        System.out.println("\nPublic Constructors:");
        for (Constructor<?> constructor : class1.getConstructors()) {
            System.out.println("  " + constructor.getName() + " (Parameters: " + constructor.getParameterCount() + ")");
        }

        // Get all declared constructors
        System.out.println("\nDeclared Constructors (all access modifiers):");
        for (Constructor<?> constructor : class1.getDeclaredConstructors()) {
            System.out.println("  " + constructor.getName() + " (Parameters: " + constructor.getParameterCount() + ")");
        }
    }
}