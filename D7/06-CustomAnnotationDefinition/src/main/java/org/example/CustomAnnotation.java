package org.example;

public class CustomAnnotation {

    @ServiceInfo(name = "UserService", version = "1.2.0", authors = {"Alice", "Bob"})
    public static class UserService {

        @Loggable("Processing user registration")
        public void registerUser(String username) {
            System.out.println("Registering user: " + username);
        }

        @Loggable(enabled = false) // Logging disabled for this method
        public void processPayment(double amount) {
            System.out.println("Processing payment of: $" + amount);
        }

        @Loggable // Uses default value for 'value'
        public void generateReport() {
            System.out.println("Generating daily report.");
        }
    }

    public static void main(String[] args) {
        // We'll process these annotations using Reflection in the next section.
        // This program mainly shows the definition and usage of custom annotations.
        UserService userService = new UserService();
        userService.registerUser("charlie");
        userService.processPayment(100.0);
        userService.generateReport();
    }
}
