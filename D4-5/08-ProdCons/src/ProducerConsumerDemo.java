import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        // ArrayBlockingQueue with a capacity of 3 items
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        int items = 10; // Number of items to produce/consume

        executor.execute(new Producer(queue, items));
        executor.execute(new Consumer(queue, items));

        executor.shutdown();
        System.out.println("Main: Initiating shutdown. Waiting for P/C to finish...");
        executor.awaitTermination(20, TimeUnit.SECONDS); // Give enough time for items to be processed
        System.out.println("Main: Producer-Consumer demo finished.");
    }
}