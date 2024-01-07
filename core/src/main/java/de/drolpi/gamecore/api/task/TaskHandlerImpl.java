package de.drolpi.gamecore.api.task;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class TaskHandlerImpl implements TaskHandler {

    private final Object instance;
    private final EnumMap<LifeCycle, List<TaskEntry>> tasks = new EnumMap<>(LifeCycle.class);
    private LifeCycle lifeCycle;

    public TaskHandlerImpl(Object instance) {
        this.instance = instance;
        this.lifeCycle = LifeCycle.STOPPED;

        for (LifeCycle moduleLifeCycle : LifeCycle.values()) {
            this.tasks.put(moduleLifeCycle, new CopyOnWriteArrayList<>());
        }
        this.findMethods();
    }

    @Override
    public void findMethods() {
        for (Method method : this.instance.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 0 && method.isAnnotationPresent(Task.class)) {
                Task task = method.getAnnotation(Task.class);
                this.tasks.get(task.event()).add(new TaskEntry(task, this.instance, method));
            }
        }
    }

    @Override
    public void fireTasks(@NotNull LifeCycle lifeCycle) {
        if(this.fireTasks(this.tasks.get(lifeCycle))) {
            this.lifeCycle = lifeCycle;
        }
    }

    public void clearTasks() {
        this.lifeCycle = null;
        this.tasks.clear();
    }

    private boolean fireTasks(List<TaskEntry> entries) {
        entries.sort(Comparator.comparingInt(o -> o.taskInfo().order()));

        for (TaskEntry entry : entries) {
            entry.handler().setAccessible(true);
            try {
                entry.handler().invoke(entry.instance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return true;
    }

    @Override
    public @NotNull LifeCycle currentLifeCycle() {
        return this.lifeCycle;
    }
}
