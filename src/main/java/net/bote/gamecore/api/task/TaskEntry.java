package net.bote.gamecore.api.task;

import java.lang.reflect.Method;

final class TaskEntry {

    private final Task taskInfo;
    private final Object instance;
    private final Method handler;

    TaskEntry(Task taskInfo, Object instance, Method handler) {
        this.taskInfo = taskInfo;
        this.instance = instance;
        this.handler = handler;
    }

    public Task taskInfo() {
        return this.taskInfo;
    }

    public Object instance() {
        return this.instance;
    }

    public Method handler() {
        return this.handler;
    }
}
