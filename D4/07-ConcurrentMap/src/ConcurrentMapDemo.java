import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapDemo {
    public static void main(String[] args) throws InterruptedException {
        // ConcurrentHashMap is designed for high concurrency with multiple readers and writers.
        Map<String, Integer> map = new ConcurrentHashMap<>();

        // Runnable to simulate multiple threads writing to the map
        Runnable writer = () -> {
            String threadName = Thread.currentThread().getName();
            for (int i = 0; i < 5000; i++) { // Increase iterations for more contention
                map.put(threadName + "-Key" + i, i);
            }
            System.out.println(threadName + " finished writing.");
        };

        // Create an ExecutorService to run multiple writer threads
        ExecutorService executor = Executors.newFixedThreadPool(4); // Use 4 threads for more contention

        long startTime = System.currentTimeMillis();

        executor.execute(writer);
        executor.execute(writer);
        executor.execute(writer);
        executor.execute(writer);

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS); // Give it more time for 20k writes

        long endTime = System.currentTimeMillis();

        System.out.println("\nMap size: " + map.size()); // Should be 20000
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
//        // If time permits, uncomment to quickly show content.
//         System.out.println("\nMap contents (partial):");
//         map.entrySet().stream().limit(10).forEach(entry -> System.out.println(entry.getKey() + "=" + entry.getValue()));
//         System.out.println("...");

        // Compare with a synchronized map (briefly mention performance implications)
        // Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        // ... if time permits, create a similar demo for syncMap to show its potentially lower performance
    }
}