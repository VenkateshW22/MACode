import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RunnerTask implements Runnable {
    private CountDownLatch startLatch;
    private CountDownLatch finishLatch;
    private String name;

    public RunnerTask(String name, CountDownLatch startLatch, CountDownLatch finishLatch) {
        this.name = name;
        this.startLatch = startLatch;
        this.finishLatch = finishLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is ready and waiting for start signal. Thread: " + Thread.currentThread().getName());
            startLatch.await(); // Wait until the start signal is given (count reaches 0)
            System.out.println(name + " has started running!");
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000) + 500); // Simulate running time
            System.out.println(name + " has finished!");
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted during race.");
            Thread.currentThread().interrupt();
        } finally {
            finishLatch.countDown(); // Decrement the finish latch when this runner is done
        }
    }
}