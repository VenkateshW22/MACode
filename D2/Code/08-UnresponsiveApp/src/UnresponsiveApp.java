// UnresponsiveApp.java
import java.util.ArrayList;
import java.util.List;

public class UnresponsiveApp {
    public static void main(String[] args) throws InterruptedException {
        List<byte[]> temporaryData = new ArrayList<>();
        while (true) {
            // Simulate allocating a lot of data that is needed for a short time
            for(int i = 0; i < 100; i++) {
                temporaryData.add(new byte[1024 * 1024]); // 1MB chunks
            }

            System.out.println("Working with " + temporaryData.size() * 1 + "MB of data...");
            Thread.sleep(1000);

            // Here's the problem: Programmer is calling System.gc() in a loop!
            // This forces a slow, unnecessary Full GC every single second.
            System.gc();

            // This doesn't actually clear the list's internal array, just the elements
            temporaryData.clear();
        }
    }
}