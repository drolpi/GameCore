package de.drolpi.gamecore.internal.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class TaskEntry {

    private final int order;
    private final LifeCycle lifeCycle;
    private final Runnable handler;

    public TaskEntry(Task taskInfo, Object instance, Method handler) {
        this.order = taskInfo.order();
        this.lifeCycle = taskInfo.event();
        this.handler = () -> {
            handler.setAccessible(true);
            try {
                handler.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

    public TaskEntry(int order, LifeCycle lifeCycle, Runnable handler) {
        this.order = order;
        this.lifeCycle = lifeCycle;
        this.handler = handler;
    }

    public int order() {
        return this.order;
    }

    public LifeCycle lifeCycle() {
        return this.lifeCycle;
    }

    public Runnable handler() {
        return this.handler;
    }
}
