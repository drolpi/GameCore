package net.bote.gamecore.api.task;

public interface TaskHandler {

    void fireTasks(LifeCycle lifeCycle);

    void clearTasks();

    void loadMethods();

    LifeCycle currentLifeCycle();

}