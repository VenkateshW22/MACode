// LeakyLogger - A logger that never clears its log history
import java.util.ArrayList;
import java.util.List;

public class LeakyLogger {
    // A static list to hold all log messages, forever.
    private static final List<String> logHistory = new ArrayList<>();

    public void log(String message) {
        String logEntry = System.currentTimeMillis() + ": " + message;
        logHistory.add(logEntry); // Ouch! This list never gets cleared.
        System.out.println(logEntry);
    }

    // A main method to simulate the logger being used over time
    public static void main(String[] args) throws InterruptedException {
        LeakyLogger logger = new LeakyLogger();
        while (true) {
            logger.log("Processing user request...");
            Thread.sleep(10); // Simulate work
        }
    }
}