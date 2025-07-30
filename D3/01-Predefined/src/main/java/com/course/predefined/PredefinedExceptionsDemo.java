package com.course.predefined;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * Demonstrates handling of several common predefined exceptions in a modular way.
 * Each type of exception is handled in its own dedicated method.
 */
public class PredefinedExceptionsDemo {

    public static void main(String[] args) {
        demonstrateArithmeticException();
        demonstrateNullPointerException();
        demonstrateArrayIndexOutOfBoundsException();
        demonstrateFileNotFoundException();
    }

    /**
     * Shows handling of ArithmeticException (e.g., division by zero).
     */
    public static void demonstrateArithmeticException() {
        System.out.println("--- Demonstrating ArithmeticException ---");
        try {
            int result = 100 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Finished arithmetic exception handling.\n");
        }
    }


    /**
     * Shows handling of NullPointerException.
     */
    public static void demonstrateNullPointerException() {
        System.out.println("--- Demonstrating NullPointerException ---");
        try {
            String str = null;
            str.length();
        } catch (NullPointerException e) {
            System.err.println("Error: Cannot invoke a method on a null object.");
        } finally {
            System.out.println("Finished null pointer exception handling.\n");
        }
    }

    /**
     * Shows handling of ArrayIndexOutOfBoundsException.
     */
    public static void demonstrateArrayIndexOutOfBoundsException() {
        System.out.println("--- Demonstrating ArrayIndexOutOfBoundsException ---");
        try {
            int[] numbers = {1, 2, 3};
            System.out.println(numbers[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Finished array index exception handling.\n");
        }
    }

    /**
     * Shows handling of a checked exception, FileNotFoundException.
     */
    public static void demonstrateFileNotFoundException() {
        System.out.println("--- Demonstrating FileNotFoundException ---");
        try {
            FileReader fr = new FileReader("non_existent_file.txt");
        } catch (FileNotFoundException e) {
            System.err.println("Error: The file could not be found.");
        } finally {
            System.out.println("Finished file not found exception handling.");
        }
    }
}
