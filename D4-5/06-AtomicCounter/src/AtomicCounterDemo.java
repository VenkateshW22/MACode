public class AtomicCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Demonstrating Atomic Variables (AtomicInteger) ---");
        AtomicCounter counter = new AtomicCounter();

        // Create threads using the new CounterRunnableAtomic
        Thread t1 = new Thread(new CounterRunnableAtomic(counter));
        Thread t2 = new Thread(new CounterRunnableAtomic(counter));

        t1.start();
        t2.start();

        t1.join(); // Wait for t1 to finish
        t2.join(); // Wait for t2 to finish

        System.out.println("Final count (AtomicInteger): " + counter.getCount());
        System.out.println("Expected: 20000, Actual: always 20000.\n");
    }
}