import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    // AtomicInteger provides methods for atomically incrementing, decrementing, etc.
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Atomically increments and returns the new value
    }

    public int getCount() {
        return count.get(); // Atomically gets the current value
    }
}