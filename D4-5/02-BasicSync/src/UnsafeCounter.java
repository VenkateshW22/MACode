public class UnsafeCounter {
    private int count = 0;

    public void increment() {
        count++; // This is not an atomic operation!
    }

    public int getCount() {
        return count;
    }
}