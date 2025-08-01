import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int numRunners = 5;
        // Latch for all runners to wait for a single "start" signal
        CountDownLatch startLatch = new CountDownLatch(1);
        // Latch for the main thread to wait for all runners to "finish"
        CountDownLatch finishLatch = new CountDownLatch(numRunners);

        ExecutorService executor = Executors.newFixedThreadPool(numRunners);

        // Create and submit all runner tasks
        for (int i = 0; i < numRunners; i++) {
            executor.execute(new RunnerTask("Runner-" + (i + 1), startLatch, finishLatch));
        }

        System.out.println("Main thread: Preparing to start race...");
        TimeUnit.SECONDS.sleep(2); // Simulate preparation time

        System.out.println("\n!!! GO GO GO !!!\n");
        startLatch.countDown(); // Signal all runners to start (decrements startLatch to 0)

        // Main thread waits for all runners to finish their tasks
        finishLatch.await(); // Blocks until finishLatch's count reaches 0
        System.out.println("\nAll runners have finished the race!");

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS); // Ensure executor shuts down cleanly
        System.out.println("Executor shut down.");
    }
}