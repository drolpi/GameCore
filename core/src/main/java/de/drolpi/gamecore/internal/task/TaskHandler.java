package de.drolpi.gamecore.internal.task;

import org.jetbrains.annotations.NotNull;

public interface TaskHandler {

    static @NotNull TaskHandler create(@NotNull Object instance) {
        return new TaskHandlerImpl(instance);
    }

    void registerHandler(TaskEntry taskEntry);

    void fireTasks(@NotNull LifeCycle lifeCycle);

    void clearTasks();

    void findMethods();

    @NotNull LifeCycle currentLifeCycle();

}
