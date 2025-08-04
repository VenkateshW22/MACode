import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private final int itemsToConsume;

    public Consumer(BlockingQueue<Integer> queue, int itemsToConsume) {
        this.queue = queue;
        this.itemsToConsume = itemsToConsume;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            for (int i = 0; i < itemsToConsume; i++) {
                int item = queue.take(); // Blocks if queue is empty
                System.out.println(threadName + ": Consumed " + item);
                TimeUnit.MILLISECONDS.sleep(150); // Simulate processing time
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + ": Consumer interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(threadName + ": Consumer finished.");
        }
    }
}