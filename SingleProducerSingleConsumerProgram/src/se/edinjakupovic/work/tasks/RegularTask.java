package se.edinjakupovic.work.tasks;


import se.edinjakupovic.work.Task;

import java.util.concurrent.ThreadLocalRandom;

public class RegularTask implements Task {
    private final int taskId;

    public RegularTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void performTask() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2500));
        } catch (InterruptedException e) {
            // In this example, lets imagine that the task cant be interrupted.
        }
        System.out.println("Completed Regular Task " + taskId);
    }
}
