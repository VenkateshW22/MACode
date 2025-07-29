import java.util.LinkedList;

public class FixedLeakyLogger {
    private static final int MAX_HISTORY = 1000;
    private static final LinkedList<String> logHistory = new LinkedList<>();

    public void log(String message) {
        String logEntry = System.currentTimeMillis() + ": " + message;

        // Synchronize to handle multi-threading safely
        synchronized(logHistory) {
            if (logHistory.size() >= MAX_HISTORY) {
                logHistory.removeFirst(); // Remove the oldest entry
            }
            logHistory.addLast(logEntry);
        }

        System.out.println(logEntry);
    }
}