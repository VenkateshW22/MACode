import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockSafeCounter {
    private int count = 0;
    private final Lock lock = new ReentrantLock(); // The ReentrantLock instance

    public void increment() {
        lock.lock(); // Acquire the lock. Blocks if another thread holds it.
        try {
            count++;
        } finally {
            // ALWAYS release the lock in a finally block to prevent deadlocks
            lock.unlock();
        }
    }

    public int getCount() {
        // For a simple read, if increment() is the only writer, no lock is strictly needed for reading.
        // However, in more complex scenarios with multiple writers or for strong memory consistency
        // guarantees, you might acquire the lock for reads as well, or use `volatile` for `count`.
        return count;
    }
}