public class MyRunnableTask implements Runnable {
    private String taskName;

    public MyRunnableTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(taskName + " running - Iteration " + i + " in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(100); // Simulate some work
            } catch (InterruptedException e) {
                System.out.println(taskName + " interrupted!");
                Thread.currentThread().interrupt(); // Re-interrupt the current thread
                return; // Exit the run method
            }
        }
        System.out.println(taskName + " finished.");
    }
}