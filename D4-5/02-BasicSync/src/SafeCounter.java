public class SafeCounter {
    private int count = 0;

    // Synchronized method: acquires the intrinsic lock on the `SafeCounter` instance
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        // Reading doesn't strictly need to be synchronized if `increment()` is the only writer.
        // For reads that could be affected by partial writes or to ensure memory visibility
        // in more complex scenarios, you might synchronize reads or make `count` volatile.
        return count;
    }
}