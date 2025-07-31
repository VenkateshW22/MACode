public class Main {
    public static void main(String[] args) {
        // This is the main thread of the application
        System.out.println("Main: Creating a new thread...");

        // 1. Create an instance of our new thread class
        MyThread t1 = new MyThread();

        // 2. Start the thread. This tells the JVM to call the run() method.
        // The main thread does NOT wait; it continues its execution immediately.
        t1.start();

        System.out.println("Main: The start() method was called. The main thread continues to run.");
        System.out.println("Main: The application will exit when all threads are finished.");
    }
}