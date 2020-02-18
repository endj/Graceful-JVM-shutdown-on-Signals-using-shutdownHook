package se.edinjakupovic.work;

import se.edinjakupovic.work.tasks.InterruptibleTask;
import se.edinjakupovic.work.tasks.NonInterruptibleTask;

public class TaskGenerator {
    private static int currentTaskId = 0;

    public static Task getNextTask(boolean interruptible) {
        if (interruptible) {
            return new InterruptibleTask(currentTaskId++);
        } else {
            return new NonInterruptibleTask();
        }
    }
}
