import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private final int itemsToProduce;

    public Producer(BlockingQueue<Integer> queue, int itemsToProduce) {
        this.queue = queue;
        this.itemsToProduce = itemsToProduce;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            for (int i = 0; i < itemsToProduce; i++) {
                int item = i + 1;
                System.out.println(threadName + ": Producing " + item);
                queue.put(item); // Blocks if queue is full
                TimeUnit.MILLISECONDS.sleep(50); // Simulate production time
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + ": Producer interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(threadName + ": Producer finished.");
        }
    }
}