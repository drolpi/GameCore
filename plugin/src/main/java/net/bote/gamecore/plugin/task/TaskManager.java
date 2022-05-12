package net.bote.gamecore.plugin.task;

import net.bote.gamecore.api.task.LifeCycle;
import net.bote.gamecore.api.task.Task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class TaskManager {

    private final Object system;
    private final EnumMap<LifeCycle, List<TaskEntry>> tasks = new EnumMap<>(LifeCycle.class);
    private LifeCycle lifeCycle;

    public TaskManager(Object system) {
        this.system = system;
        this.lifeCycle = LifeCycle.STOPPED;

        for (LifeCycle moduleLifeCycle : LifeCycle.values()) {
            this.tasks.put(moduleLifeCycle, new CopyOnWriteArrayList<>());
        }
    }

    public void findMethods() {
        for (Method method : system.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 0 && method.isAnnotationPresent(Task.class)) {
                Task task = method.getAnnotation(Task.class);
                this.tasks.get(task.event()).add(new TaskEntry(task, system, method));
            }
        }
    }

    public void fireTasks(LifeCycle lifeCycle) {
        if(this.fireTasks(this.tasks.get(lifeCycle))) {
            this.lifeCycle = lifeCycle;
        }
    }

    public void clearTasks() {
        lifeCycle = null;
        tasks.clear();
    }

    private boolean fireTasks(List<TaskEntry> entries) {
        entries.sort(Comparator.comparingInt(o -> o.taskInfo().order()));

        for (TaskEntry entry : entries) {
            entry.handler().setAccessible(true);
            try {
                entry.handler().invoke(entry.system());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return true;
    }

    public LifeCycle currentLifeCycle() {
        return lifeCycle;
    }
}