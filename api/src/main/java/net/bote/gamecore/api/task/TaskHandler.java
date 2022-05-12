package net.bote.gamecore.api.task;

import org.jetbrains.annotations.NotNull;

public interface TaskHandler {

    void fireTasks(@NotNull LifeCycle lifeCycle);

    void clearTasks();

    void loadMethods();

    @NotNull LifeCycle currentLifeCycle();

}