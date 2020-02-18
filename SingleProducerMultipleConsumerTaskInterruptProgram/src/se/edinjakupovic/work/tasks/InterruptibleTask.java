package se.edinjakupovic.work.tasks;

import se.edinjakupovic.work.Task;
import se.edinjakupovic.work.exception.TaskCancelledException;

public class InterruptibleTask implements Task {

    private final int taskId;

    public InterruptibleTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void performTask() throws TaskCancelledException {
        while (true) {
            try {
                Thread.sleep(9999);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TaskCancelledException(String.format("Task %d got interrupted on thread [%s]!", taskId, Thread.currentThread().getName()), taskId);
            }
        }
    }


}
