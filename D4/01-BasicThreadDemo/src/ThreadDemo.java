public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println("Main thread started. Thread: " + Thread.currentThread().getName());

        // Create instances of our runnable tasks
        Runnable task1 = new MyRunnableTask("Task A");
        Runnable task2 = new MyRunnableTask("Task B");

        // Create Thread objects from the runnable tasks
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        // Start the threads (calls run() in a new thread of execution)
        thread1.start();
        thread2.start();

        System.out.println("Main thread finished starting tasks. Thread: " + Thread.currentThread().getName());
        // Note: Main thread continues immediately. Output will be interleaved.
    }
}