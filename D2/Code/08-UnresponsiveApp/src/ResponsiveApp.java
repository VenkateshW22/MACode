// ResponsiveApp.java
import java.util.ArrayList;
import java.util.List;

public class ResponsiveApp {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            // Data is now scoped locally, which is much better practice
            List<byte[]> temporaryData = new ArrayList<>();
            for(int i = 0; i < 100; i++) {
                temporaryData.add(new byte[1024 * 1024]);
            }

            System.out.println("Working with " + temporaryData.size() + "MB of data...");
            Thread.sleep(1000);

            // By removing System.gc() and letting the list go out of scope,
            // the JVM will efficiently clean this up with fast, Minor GCs.
        }
    }
}