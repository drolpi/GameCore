package net.bote.gamecore.plugin.task;

import net.bote.gamecore.api.task.Task;

import java.lang.reflect.Method;

final class TaskEntry {

    private final Task taskInfo;
    private final Object system;
    private final Method handler;

    TaskEntry(Task taskInfo, Object system, Method handler) {
        this.taskInfo = taskInfo;
        this.system = system;
        this.handler = handler;
    }

    public Task taskInfo() {
        return this.taskInfo;
    }

    public Object system() {
        return this.system;
    }

    public Method handler() {
        return this.handler;
    }
}