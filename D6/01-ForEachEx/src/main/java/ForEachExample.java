import java.util.Arrays;
import java.util.List;

public class ForEachExample {
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java", "Python", "JavaScript", "C#");

        // Before Java 8: External iteration with a for-each loop
        System.out.println("--- Before Java 8 (External Iteration) ---");
        for (String lang : languages) {
            System.out.println(lang);
        }

        // After Java 8: Internal iteration with forEach and a lambda
        // The forEach method takes a Consumer<T> functional interface.
        System.out.println("\n--- With Java 8 (Internal Iteration) ---");
        languages.forEach(lang -> System.out.println(lang));

        // Even shorter with a Method Reference (covered in Hour 2!)
        System.out.println("\n--- With Java 8 (Method Reference) ---");
        languages.forEach(System.out::println);
    }
}