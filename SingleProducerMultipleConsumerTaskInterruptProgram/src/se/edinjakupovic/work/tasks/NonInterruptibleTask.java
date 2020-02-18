package se.edinjakupovic.work.tasks;

import se.edinjakupovic.work.Task;

public class NonInterruptibleTask implements Task {

    @Override
    public void performTask() {
        while (true) {
            try {
                Thread.sleep(99999);
            } catch (InterruptedException e) {
                System.out.println(":^)");
            }
        }
    }
}
