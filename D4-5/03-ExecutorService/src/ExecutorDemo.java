import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread started. Thread: " + Thread.currentThread().getName());

        // --- Using Fixed Thread Pool ---
        System.out.println("\n--- Fixed Thread Pool (2 threads) ---");
        // Creates a thread pool with a fixed number of threads. Tasks will queue up if all threads are busy.
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) { // Submit 5 tasks
            fixedPool.execute(new MyRunnableTask("Fixed-Task " + (i + 1)));
        }
        fixedPool.shutdown(); // Initiate orderly shutdown: no new tasks accepted, existing tasks finish.
        System.out.println("Fixed pool shutdown initiated. Waiting for tasks to complete...");
        // awaitTermination blocks until all tasks complete or timeout occurs
        boolean terminatedFixed = fixedPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Fixed pool terminated: " + terminatedFixed);


        // --- Using Single Thread Executor ---
        System.out.println("\n--- Single Thread Executor ---");
        // Creates an executor that uses a single worker thread. Tasks are guaranteed to execute sequentially.
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) { // Submit 3 tasks
            singlePool.execute(new MyRunnableTask("Single-Task " + (i + 1)));
        }
        singlePool.shutdown();
        System.out.println("Single pool shutdown initiated. Waiting for tasks to complete...");
        boolean terminatedSingle = singlePool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Single pool terminated: " + terminatedSingle);

        System.out.println("\nMain thread finished. Thread: " + Thread.currentThread().getName());
    }
}