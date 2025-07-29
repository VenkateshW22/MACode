public class University {
    public static void main(String[] args) {
        // Create student objects using the parameterized constructor.
        Student student1 = new Student("Arjun", 101, 9.2);
        Student student2 = new Student("Sneha", 102, 9.5);

        System.out.println("Information for Student 1:");
        student1.displayInfo(); // Calls the method to print Arjun's data.

        System.out.println("\nInformation for Student 2:");
        student2.displayInfo(); // Calls the method to print Sneha's data.

        // Modify student2's GPA using the setter (also demonstrates how encapsulation works).
        student2.setGpa(9.6);

        // Use getter to access the updated value.
        System.out.println("\nUpdated GPA for Sneha: " + student2.getGpa());
    }
}
