import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;

public class CompletableFutureChainingDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Optional: A custom executor to control thread pool used by CompletableFuture async methods
        ExecutorService customExecutor = Executors.newFixedThreadPool(3);

        System.out.println("Main thread (" + Thread.currentThread().getName() + "): Starting asynchronous operations...");

        // 1. CompletableFuture.supplyAsync: Executes a Supplier task, returns a result
        CompletableFuture<String> initialFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1 (supplyAsync): Fetching raw data in " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2); // Simulate long-running I/O or computation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Raw Data from External API";
        }, customExecutor); // Use custom executor for this initial task

        // 2. thenApplyAsync: Transforms the result of the previous stage, runs in a separate thread
        CompletableFuture<String> processedFuture = initialFuture.thenApplyAsync(data -> {
            System.out.println("Step 2 (thenApplyAsync): Processing '" + data + "' in " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1); // Simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return data.toUpperCase() + " (PROCESSED)";
        }, customExecutor); // Use custom executor for this chained async operation

        // 3. thenAccept: Consumes the result of the previous stage (no return value)
        // Uses the same thread as the previous stage if it completes synchronously, or a new async thread
        processedFuture.thenAccept(finalResult -> {
            System.out.println("Step 3 (thenAccept): Final result is: '" + finalResult + "' in " + Thread.currentThread().getName());
        });

        // 4. thenRun: Executes a Runnable after the previous stage completes (no input, no return value)
        processedFuture.thenRun(() -> {
            System.out.println("Step 4 (thenRun): All chained processing complete! in " + Thread.currentThread().getName());
        });

        // 5. Another example using runAsync (no return value)
        CompletableFuture.runAsync(() -> {
            System.out.println("Another independent task (runAsync) in " + Thread.currentThread().getName());
            try { TimeUnit.MILLISECONDS.sleep(700); } catch (InterruptedException e) {}
            System.out.println("Another independent task finished.");
        }, customExecutor);


        System.out.println("Main thread (" + Thread.currentThread().getName() + "): Operations submitted. I can do other work now...");
        // Main thread continues immediately. We need to keep it alive to see async results.
        TimeUnit.SECONDS.sleep(4); // Give enough time for most (if not all) tasks to complete
        System.out.println("Main thread (" + Thread.currentThread().getName() + "): Done waiting and exiting.");

        customExecutor.shutdown(); // Shut down the custom executor
        customExecutor.awaitTermination(5, TimeUnit.SECONDS); // Wait for tasks to finish before truly exiting
    }
}