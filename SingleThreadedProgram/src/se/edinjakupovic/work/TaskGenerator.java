package se.edinjakupovic.work;

import se.edinjakupovic.work.tasks.RegularTask;

public class TaskGenerator {

    public static Task getNextTask() {
        return new RegularTask();
    }

}
