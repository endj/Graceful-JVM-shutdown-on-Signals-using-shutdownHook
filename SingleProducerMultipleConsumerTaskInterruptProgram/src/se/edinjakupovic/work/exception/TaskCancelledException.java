package se.edinjakupovic.work.exception;


public class TaskCancelledException extends InterruptedException {
    private int taskId;

    public TaskCancelledException(String message, int taskId) {
        super(message);
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }
}
