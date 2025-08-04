public class CounterRunnable implements Runnable {
    private UnsafeCounter unsafeCounter; // Can hold UnsafeCounter or SafeCounter
    private SafeCounter safeCounter;

    // Constructor for UnsafeCounter
    public CounterRunnable(UnsafeCounter counter) {
        this.unsafeCounter = counter;
    }

    // Constructor for SafeCounter
    public CounterRunnable(SafeCounter counter) {
        this.safeCounter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) { // Increment 10,000 times
            if (unsafeCounter != null) {
                unsafeCounter.increment();
            } else if (safeCounter != null) {
                safeCounter.increment();
            }
        }
    }
}