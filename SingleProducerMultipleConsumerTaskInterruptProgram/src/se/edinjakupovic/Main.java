package se.edinjakupovic;

import se.edinjakupovic.work.Task;
import se.edinjakupovic.work.TaskGenerator;
import se.edinjakupovic.work.exception.TaskCancelledException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int QUEUE_SIZE = 10;
    private static final int WORKERS = 5;
    private static final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);

    public static void main(String[] args) {
        ThreadPoolExecutor consumers = (ThreadPoolExecutor) Executors.newFixedThreadPool(WORKERS);
        ExecutorService producer = Executors.newSingleThreadExecutor();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            producer.shutdownNow();
            consumers.shutdownNow();

            try {
                boolean terminated = consumers.awaitTermination(15, TimeUnit.SECONDS);
                System.out.println("All tasks terminated: " + terminated);
                System.out.println("Tasks left in queue: " + taskQueue.size());
                System.out.println("Approximately number of executing threads after shutdown: " + consumers.getActiveCount());
            } catch (InterruptedException e) {
                // thrown if awaitTermination thread is interrupted
            }
        }));

        producer.execute(() -> {
            while (true) {
                Task nextTask = TaskGenerator.getNextTask(true);
                try {
                    taskQueue.put(nextTask);
                } catch (InterruptedException e) {
                    // Thrown if someone interrupts the producer thread while waiting on put
                }
            }
        });

        for (int i = 0; i < WORKERS; i++) {
            consumers.execute(() -> {
                try {
                    Task task = taskQueue.take();
                    task.performTask();
                } catch (TaskCancelledException tce) {
                    System.out.println(tce.getMessage());
                    boolean wasInterrupted = Thread.currentThread().isInterrupted();
                    // Handle it
                } catch (InterruptedException e) {
                    /**
                     * Thrown if thread is interrupted while blocked in {@link BlockingQueue#take()} ()}}.
                     */
                    System.out.println(e.getMessage());
                }
            });
        }
    }
}
