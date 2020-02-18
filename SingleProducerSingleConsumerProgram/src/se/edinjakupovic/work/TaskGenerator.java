package se.edinjakupovic.work;


import se.edinjakupovic.work.tasks.RegularTask;

public class TaskGenerator {
    private static int currentTaskId = 0;

    public static Task getNextTask() {
        return new RegularTask(currentTaskId++);
    }
}
