package com.course.advstreamops;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// (Employee class is defined as in Lab 2)
public class MainApp {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 28, "Engineering", 90000),
                new Employee("Bob", 32, "HR", 75000),
                new Employee("Charlie", 35, "Engineering", 120000),
                new Employee("David", 25, "Sales", 60000),
                new Employee("Eve", 40, "Engineering", 150000),
                new Employee("Frank", 29, "Engineering", 100000)
        );

        // We use groupingBy to classify employees by department.
        // The second argument to groupingBy is a "downstream collector" that operates
        // on the list of values for each key.
        // Collectors.averagingDouble() calculates the average of a double-valued function.
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,                 // The classifier function (produces the map keys)
                        Collectors.averagingDouble(Employee::getSalary) // The downstream collector (produces the map values)
                ));

        System.out.println("Average Salary by Department:");
        avgSalaryByDept.forEach((dept, avgSalary) ->
                System.out.printf("%s: $%.2f%n", dept, avgSalary)
        );
        // Expected Output:
        // HR: $75000.00
        // Engineering: $115000.00 ( (90000+120000+150000+100000)/4 )
        // Sales: $60000.00
    }
}