package com.course.stringsorter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringSorter {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        // 1. Sort alphabetically (natural order)
        // The lambda (s1, s2) -> s1.compareTo(s2) implements the compare method of the Comparator interface.
        System.out.println("--- Sorting Alphabetically (Natural Order) ---");
        // Collections.sort takes a List and a Comparator. The lambda provides the comparison logic.
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
        names.forEach(name -> System.out.println(name));
        // Result: [Alice, Bob, Charlie, David, Eve]

        // Create a new list for the second sort to show distinct results
        List<String> tech = Arrays.asList("Java", "Go", "Rust", "Python", "C++", "Kotlin");

        // 2. Sort by length
        // The lambda (s1, s2) -> Integer.compare(s1.length(), s2.length()) provides the sorting logic.
        System.out.println("\n--- Sorting by Length ---");

        // Using List.sort (added in Java 8) which takes a Comparator directly
        tech.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
        // Note: For equal lengths, the original relative order might not be preserved depending on sort algorithm.
        // Example: "Go" (2) "C++" (3) "Java" (4) "Rust" (4) "Python" (6) "Kotlin" (6)
        // If "Rust" came before "Java" initially, their order is stable with stable sorts.
        // List.sort is stable.

        // 3. Iterate and print the final sorted list
        System.out.println("Final list sorted by length:");
        tech.forEach(t -> System.out.println(t));
        // Expected output (may vary slightly for equal length elements depending on stability):
        // Go
        // C++
        // Java
        // Rust
        // Python
        // Kotlin
    }
}