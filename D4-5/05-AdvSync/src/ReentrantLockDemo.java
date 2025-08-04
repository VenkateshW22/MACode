import java.util.concurrent.TimeUnit;

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Demonstrating ReentrantLock ---");
        LockSafeCounter counter = new LockSafeCounter();

        // Create threads using the new CounterRunnableReentrant
        Thread t1 = new Thread(new CounterRunnableReentrant(counter));
        Thread t2 = new Thread(new CounterRunnableReentrant(counter));

        t1.start();
        t2.start();

        t1.join(); // Wait for t1 to finish
        t2.join(); // Wait for t2 to finish

        System.out.println("Final count (ReentrantLock): " + counter.getCount());
        System.out.println("Expected: 20000, Actual: always 20000.\n");
    }
}