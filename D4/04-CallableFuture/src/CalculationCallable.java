import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CalculationCallable implements Callable<Long> { // Returns a Long
    private int number;

    public CalculationCallable(int number) {
        this.number = number;
    }

    @Override
    public Long call() throws Exception {
        System.out.println("CalculationCallable: Summing up to " + number + " in thread: " + Thread.currentThread().getName());
        long sum = 0;
        for (int i = 1; i <= number; i++) {
            sum += i;
            TimeUnit.MILLISECONDS.sleep(10); // Simulate some work
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("CalculationCallable: Interrupted during calculation for " + number);
                throw new InterruptedException("Calculation interrupted"); // Propagate interrupt as exception
            }
        }
        System.out.println("CalculationCallable: Finished sum for " + number);
        return sum;
    }
}