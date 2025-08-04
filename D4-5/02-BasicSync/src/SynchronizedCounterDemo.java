public class SynchronizedCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        // --- Demonstrate Race Condition with UnsafeCounter ---
        System.out.println("--- Demonstrating Race Condition (UnsafeCounter) ---");
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        Thread t1_unsafe = new Thread(new CounterRunnable(unsafeCounter));
        Thread t2_unsafe = new Thread(new CounterRunnable(unsafeCounter));

        t1_unsafe.start();
        t2_unsafe.start();

        t1_unsafe.join(); // Wait for t1_unsafe to finish
        t2_unsafe.join(); // Wait for t2_unsafe to finish

        System.out.println("Final count (Unsafe): " + unsafeCounter.getCount());
        System.out.println("Expected: 20000, Actual: often less than 20000 due to race condition.\n");

        // --- Demonstrate Fix with SafeCounter (synchronized) ---
        System.out.println("--- Demonstrating Fix with synchronized (SafeCounter) ---");
        SafeCounter safeCounter = new SafeCounter();
        Thread t1_safe = new Thread(new CounterRunnable(safeCounter));
        Thread t2_safe = new Thread(new CounterRunnable(safeCounter));

        t1_safe.start();
        t2_safe.start();

        t1_safe.join(); // Wait for t1_safe to finish
        t2_safe.join(); // Wait for t2_safe to finish

        System.out.println("Final count (Safe): " + safeCounter.getCount());
        System.out.println("Expected: 20000, Actual: always 20000.\n");
    }
}