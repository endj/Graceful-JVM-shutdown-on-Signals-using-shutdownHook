package se.edinjakupovic.work.tasks;

import se.edinjakupovic.work.Task;

import java.util.logging.Logger;

public class RegularTask implements Task {
    private static final Logger log = Logger.getLogger(RegularTask.class.getName());

    @Override
    public void performTask() {
        log.info("Performing task");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // We wont handle interrupts in this example
        }
        /**
         * Note that this log row does not always write even though the task is
         * completed. If we replace it with System.out.print it will always print.
         * This is due to the logging manager also having a shutdownHook which can
         * run before the log is written.
         */
        log.info("Completed Regular Task");
    }
}
