// This is a new version of CounterRunnable specifically for LockSafeCounter
public class CounterRunnableReentrant implements Runnable {
    private LockSafeCounter counter;

    public CounterRunnableReentrant(LockSafeCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.increment();
        }
    }
}