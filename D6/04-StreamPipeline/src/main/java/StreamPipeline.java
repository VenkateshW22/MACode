import java.util.Arrays;
import java.util.List;

public class StreamPipeline {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 28, "Engineering", 90000),
                new Employee("Bob", 32, "HR", 75000),
                new Employee("Charlie", 35, "Engineering", 120000),
                new Employee("David", 25, "Sales", 60000),
                new Employee("Eve", 40, "Engineering", 150000),
                new Employee("Frank", 29, "Engineering", 100000) // Added for more examples
        );

        System.out.println("Engineers over 30 (Names in Uppercase):");
        employees.stream()                                   // 1. Get the stream from the source list
                .filter(e -> "Engineering".equals(e.getDepartment())) // 2. Find employees in "Engineering" (Intermediate Operation)
                .filter(e -> e.getAge() > 30)               // 3. Filter for those over the age of 30 (Intermediate Operation)
                .map(Employee::getName)                     // 4. Extract their names (Intermediate Operation)
                .map(String::toUpperCase)                   // 5. Convert the names to uppercase (Intermediate Operation)
                .forEach(System.out::println);              // 6. Print each result (Terminal Operation)
        // Expected output:
        // CHARLIE
        // EVE
    }
}