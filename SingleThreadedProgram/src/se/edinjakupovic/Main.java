package se.edinjakupovic;

import se.edinjakupovic.work.Task;
import se.edinjakupovic.work.TaskGenerator;


public class Main {

    private static volatile boolean shouldRun = true;

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Attempting graceful shut down");
            shouldRun = false;
            try {
                /**
                 * By calling join on the main thread, we are blocking
                 * until the main thread has finished doing any cleanup
                 * after exiting the main loop.
                 */
                mainThread.join();
                System.out.println("Main thread done shutting down");
            } catch (InterruptedException e) {
                /**
                 * While it's not necessary in this example, generally
                 * when a method throws InterruptedException, it has done
                 * so by calling {@link Thread#isInterrupted()}}. Doing so
                 * resets the interruption flag. After an InterruptedException
                 * is handled calling interrupt again preserves the information
                 * that the thread has exited early due to interruption.
                 */
                Thread.currentThread().interrupt();
            }
        }));

        while (shouldRun) {
            Task nextTask = TaskGenerator.getNextTask();
            nextTask.performTask();
        }
        // Do some additional cleanup if needed
        System.out.println("Doing cleanup");
    }

}
