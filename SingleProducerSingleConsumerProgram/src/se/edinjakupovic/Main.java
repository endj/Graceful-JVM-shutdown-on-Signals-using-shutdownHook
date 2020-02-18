package se.edinjakupovic;

import se.edinjakupovic.work.Task;
import se.edinjakupovic.work.TaskGenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ExecutorService consumer = Executors.newSingleThreadExecutor();
        ExecutorService producer = Executors.newSingleThreadExecutor();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            /**
             * In the producer case we will never terminate normally so calling
             * shutdown will never have the thread terminating. Instead we issue
             * a shutdownNow() call which will call interrupt on every thread in the
             * producer ExecutorsService. We can check the interrupted flag to detect if we
             * have been interrupted and gracefully exit.
             */
            System.out.println("Attempting to shutdown producerPool");
            producer.shutdownNow();

            /**
             * In this case we have a single thread which performs tasks that last between
             * 0.5 and 2.5 seconds. Calling shutdown will reject any further tasks from
             * being submitted to the executor queue. Any currently running tasks
             * and tasks in the queue will be completed before the executor service
             * terminates.
             */
            System.out.println("Attempting to shutdown workerPool");
            consumer.shutdown();

            try {
                /**
                 * Previously we called shutdown which wont end the program until all
                 * tasks in the consumer executor queue are completed. Using awaitTermination, we
                 * can block until all tasks are completed after calling shutdown. Optionally, we
                 * can provide a timeout duration where we can terminate early.
                 *
                 * If we don't want to wait for all tasks to complete, we can issue a shutdownNow call.
                 * Calling shutdownNow, calls interrupt on all executing threads
                 * and returns any tasks still in the queue as a List. These tasks can then be handled.
                 * Calling shutdownNow does not guarantee that the currently running tasks are stopped.
                 * Interruption logic needs to be handled in executing threads.
                 */
                boolean terminated = consumer.awaitTermination(1, TimeUnit.SECONDS);
                if (!terminated) {
                    List<Runnable> runnables = consumer.shutdownNow();
                    // Handle tasks which could not be run.
                    System.out.println("Forcing shutdown after 5 seconds, tasks left in queue: " + runnables.size());
                }
            } catch (InterruptedException e) {
                consumer.shutdown();
                // thrown by the thread calling awaitTermination if interrupted while waiting.
            }
        }));

        producer.execute(() -> {
            while (true) {

                /**
                 * We can check if the current thread executing
                 * has been interrupted by another thread by calling isInterrupted.
                 * If that is the case we can exit gracefully.
                 * !Note By calling isInterrupted , it resets the interruption flag to false.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Got interrupted");
                    return;
                }

                Task nextTask = TaskGenerator.getNextTask();
                try {
                    consumer.execute(nextTask::performTask);
                } catch (RejectedExecutionException e) {
                    /**
                     * If the consumer ExecutorsService has been shutdown or if it cant
                     * accept a runnable for any other reason, it can throw a RejectedExecutionException.
                     * Depending on the program logic, we might want to stop producing tasks now.
                     */
                    return;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Got interrupted while sleeping");
                    return;
                }
            }
        });
    }
}
