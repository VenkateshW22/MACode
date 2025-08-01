// This is a new version of CounterRunnable specifically for AtomicCounter
public class CounterRunnableAtomic implements Runnable {
    private AtomicCounter counter;

    public CounterRunnableAtomic(AtomicCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter.increment();
        }
    }
}