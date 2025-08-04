import java.util.concurrent.*;

public class CallableFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2); // Using a fixed pool for consistency

        System.out.println("Main thread: Submitting tasks...");

        Callable<Long> task1 = new CalculationCallable(100);
        Callable<Long> task2 = new CalculationCallable(50);

        Future<Long> future1 = executor.submit(task1);
        Future<Long> future2 = executor.submit(task2);

        // Main thread can do other work here...
        System.out.println("Main thread: Doing other work while tasks execute...");
        TimeUnit.MILLISECONDS.sleep(200); // Simulate some other work

        // Retrieve results (this will block until results are available)
        System.out.println("Main thread: Retrieving results...");
        try {
            Long result1 = future1.get(); // Blocks until task1 completes
            System.out.println("Main thread: Result of task 1: " + result1);

            Long result2 = future2.get(); // Blocks until task2 completes
            System.out.println("Main thread: Result of task 2: " + result2);
        } catch (ExecutionException e) {
            System.err.println("Main thread: Error getting future result: " + e.getCause().getMessage());
        }

        // --- Demonstrate cancel (briefly) ---
        System.out.println("\n--- Demonstrating Future Cancellation ---");
        Callable<Long> taskToCancel = new CalculationCallable(200);
        Future<Long> futureToCancel = executor.submit(taskToCancel);

        // Give it a moment to potentially start
        TimeUnit.MILLISECONDS.sleep(50);

        // Attempt to cancel the task. true means it should interrupt the running thread if possible.
        boolean cancelled = futureToCancel.cancel(true);
        System.out.println("Main thread: Task to cancel was cancelled: " + cancelled);

        try {
            // Attempting to get the result of a cancelled future will throw CancellationException
            futureToCancel.get();
        } catch (CancellationException e) {
            System.out.println("Main thread: Caught CancellationException as expected for cancelled task.");
        } catch (ExecutionException e) {
            System.err.println("Main thread: Caught ExecutionException for cancelled task: " + e.getCause().getMessage());
        }


        executor.shutdown(); // Initiate orderly shutdown
        // Wait for all tasks to complete, or timeout
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Main thread: Executor shut down: " + terminated);
    }
}