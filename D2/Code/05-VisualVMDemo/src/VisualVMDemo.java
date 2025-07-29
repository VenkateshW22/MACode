// VisualVMDemo - VisualVM used to track memory usage
import java.util.ArrayList;
import java.util.List;

public class VisualVMDemo {
    // Long-lived objects will end up in the Old Generation
    private static final List<Object> longLivedList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("VisualVM Demo Running. Connect now!");
        Thread.sleep(5000); // Time to connect VisualVM

        while (true) {
            createAndDiscardObjects(1000); // These should die young
            createAndKeepObjects(50);      // These should be promoted
            Thread.sleep(100);
        }
    }

    public static void createAndDiscardObjects(int count) {
        for (int i = 0; i < count; i++) {
            new Object(); // Created in Eden, and instantly becomes garbage
        }
    }

    public static void createAndKeepObjects(int count) {
        for (int i = 0; i < count; i++) {
            longLivedList.add(new byte[1024]); // Keep a reference
        }
    }
}