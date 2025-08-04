package com.course.variablescope;
public class VariableScope {
    static String staticMessage = "Static World";
    String instanceMessage = "Instance World"; // Instance variable

    public void demo() {
        String localMessage = "Local World"; // This is effectively final
        String finalMessage = "Final World"; // Explicitly final

        // This lambda can access all three variables
        Runnable r = () -> {
            System.out.println("Hello from " + staticMessage); // Access static
            System.out.println("Hello from " + instanceMessage); // Access instance
            System.out.println("Hello from " + localMessage);   // Access effectively final local
            System.out.println("Hello from " + finalMessage);   // Access final local
        };

        new Thread(r).start();

        // If you uncomment the line below, it causes a compile error!
        // The lambda requires 'localMessage' to be effectively final.
        // localMessage = "New Value"; // This line would make 'localMessage' NOT effectively final
    }

    public static void main(String[] args) {
        new VariableScope().demo();
    }
}

