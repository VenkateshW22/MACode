/**
 * Defines a new thread of execution by extending the Thread class.
 * The logic for the new thread is placed inside the run() method.
 */
class MyThread extends Thread {
    @Override
    public void run() {
        // This code will be executed in a separate thread
        System.out.println("MyThread: The new thread is running!");

        // Simulate some work
        try {
            Thread.sleep(2000); // Pause for 2 seconds
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }

        System.out.println("MyThread: The new thread has finished its work.");
    }
}