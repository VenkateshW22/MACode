import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class CompletableFutureCombineErrorDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool(); // Good for I/O bound tasks

        System.out.println("Main thread: Starting combine and error handling demo...");

        // --- Combining two independent futures using thenCombine() ---
        System.out.println("\n--- Demonstrating thenCombine() ---");
        CompletableFuture<String> futureUser = CompletableFuture.supplyAsync(() -> {
            System.out.println("FutureUser: Fetching user data...");
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "User: Alice";
        }, executor);

        CompletableFuture<Integer> futureOrders = CompletableFuture.supplyAsync(() -> {
            System.out.println("FutureOrders: Fetching order count...");
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return 5;
        }, executor);

        // thenCombine takes another CompletableFuture and a BiFunction to combine their results
        CompletableFuture<String> combinedFuture = futureUser.thenCombine(futureOrders, (user, orders) -> {
            System.out.println("Combined Stage: Processing user and orders in " + Thread.currentThread().getName());
            return user + " has " + orders + " orders.";
        });

        System.out.println("Combined result: " + combinedFuture.get()); // Blocks to get final combined result

        System.out.println("\n--- Demonstrating Error Handling with exceptionally() ---");

        // CompletableFuture that will fail
        CompletableFuture<String> failingFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Failing Future: Attempting to connect to external service...");
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            throw new RuntimeException("Simulated Service Unavailable Error!"); // Simulate an error
        }, executor);

        // exceptionally(): Provides a recovery mechanism if the previous stage throws an exception
        CompletableFuture<String> recoveryFuture = failingFuture.exceptionally(ex -> {
            System.err.println("Caught exception in exceptionally(): " + ex.getMessage() + " in " + Thread.currentThread().getName());
            return "Default Data (due to error)"; // Return a fallback value
        });

        System.out.println("Recovery result: " + recoveryFuture.get()); // Get the recovered value or actual result

        System.out.println("\n--- Demonstrating Error Handling with handle() ---");

        // Using handle() for logging/cleanup regardless of success/failure
        CompletableFuture<String> handledFuture = CompletableFuture.supplyAsync(() -> {
                    System.out.println("Handled Future: Performing some operation...");
                    // Uncomment the line below to simulate a random failure
                    // if (Math.random() > 0.5) throw new RuntimeException("Random Failure Occurred!");
                    try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    return "Operation Success";
                }, executor)
                .handle((result, ex) -> { // handle takes both the result and the exception (one will be null)
                    if (ex != null) {
                        System.err.println("Handle Stage: Operation failed with: " + ex.getMessage() + " in " + Thread.currentThread().getName());
                        return "Handled Failure Value"; // Can return a new value for the next stage
                    } else {
                        System.out.println("Handle Stage: Operation succeeded with: " + result + " in " + Thread.currentThread().getName());
                        return result + " (Processed in Handle)";
                    }
                });

        System.out.println("Handled Future Result: " + handledFuture.get());

        System.out.println("\n--- Demonstrating allOf() ---");

        // allOf(): Waits for all given CompletableFutures to complete. Returns CompletableFuture<Void>.
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> { try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) {} System.out.println("Task A Done"); }, executor),
                CompletableFuture.runAsync(() -> { try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) {} System.out.println("Task B Done"); }, executor),
                CompletableFuture.runAsync(() -> { try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) {} System.out.println("Task C Done"); }, executor)
        );

        allOfFuture.get(); // Blocks until all tasks A, B, and C are done
        System.out.println("All tasks from allOf() are complete.");

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Executor shut down.");
    }
}