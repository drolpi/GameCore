package de.drolpi.gamecore.api.counter;

import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.counter.count.CountingRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

final class CounterImpl implements Counter {

    private final GamePlugin plugin;
    private final BukkitScheduler scheduler;
    private final long startCount;
    private final long stopCount;
    private final long tick;
    private final TimeUnit tickUnit;
    private final int step;
    private final Consumer<Counter> startHandler;
    private final Consumer<Counter> tickHandler;
    private final Consumer<Counter> finishHandler;
    private final Consumer<Counter> cancelHandler;

    private volatile CountingRunnable runnable;
    private volatile int taskId = -1;
    private volatile CounterStatus status;

    CounterImpl(final BuilderImpl builder) {
        this.plugin = builder.plugin;
        this.scheduler = this.plugin.getServer().getScheduler();

        this.startHandler = builder.startHandler;
        this.tickHandler = builder.tickHandler;
        this.finishHandler = builder.finishHandler;
        this.cancelHandler = builder.cancelHandler;
        this.startCount = builder.startCount;
        this.stopCount = builder.stopCount;
        this.tick = builder.tick;
        this.tickUnit = builder.tickUnit;
        this.status = CounterStatus.IDLING;
        this.step = this.stopCount > builder.startCount ? 1 : -1;
    }

    @Override
    public void start() {
        if (this.taskId != -1) {
            throw new IllegalStateException("This counter is already running");
        }

        this.runnable = CountingRunnable
            .builder()
            .step(this.step)
            .initialCount(this.startCount)
            .condition(this::condition)
            .callback(this::tick)
            .build();
        this.taskId = this.scheduler.scheduleSyncRepeatingTask(this.plugin, this.runnable, 0, tickUnit.toSeconds(tick) * 20);

        this.status = CounterStatus.RUNNING;
        this.handleStart();
    }

    @Override
    public void pause() {
        if (this.status != CounterStatus.RUNNING) {
            return;
        }
        this.status = CounterStatus.PAUSED;
    }

    @Override
    public void resume() {
        if (this.status != CounterStatus.PAUSED) {
            return;
        }
        this.status = CounterStatus.RUNNING;
    }

    @Override
    public void stop() {
        if (this.status == CounterStatus.IDLING) {
            return;
        }
        this.cancel(this::handleCancel);
    }

    @Override
    public boolean isPaused() {
        return this.status == CounterStatus.PAUSED;
    }

    @Override
    public boolean isRunning() {
        return this.status == CounterStatus.RUNNING;
    }

    @Override
    public long tickedCount() {
        return (this.startCount - this.runnable.count()) * -this.step;
    }

    @Override
    public long currentCount() {
        return this.runnable.count();
    }

    @Override
    public void currentCount(final long count) {
        this.runnable.count(count);
    }

    @Override
    public @NotNull TimeUnit tickUnit() {
        return this.tickUnit;
    }

    @Override
    public @NotNull CounterStatus status() {
        return this.status;
    }

    @Override
    public long startCount() {
        return this.startCount;
    }

    @Override
    public long stopCount() {
        return this.stopCount;
    }

    @Override
    public long tickValue() {
        return this.tick;
    }

    private void handleStart() {
        if (this.startHandler == null) {
            return;
        }
        this.startHandler.accept(this);
    }

    private void handleTick() {
        if (this.tickHandler == null) {
            return;
        }
        this.tickHandler.accept(this);
    }

    private void handleFinish() {
        if (this.finishHandler == null) {
            return;
        }
        this.finishHandler.accept(this);
    }

    private void handleCancel() {
        if (this.cancelHandler == null) {
            return;
        }
        this.cancelHandler.accept(this);
    }

    private void cancel(final Runnable callback) {
        this.status = CounterStatus.IDLING;
        if (this.taskId == -1) {
            return;
        }
        this.scheduler.cancelTask(this.taskId);
        this.taskId = -1;
        if (callback == null) {
            return;
        }
        callback.run();
    }

    private boolean condition() {
        if (this.status != CounterStatus.RUNNING) {
            return false;
        }

        this.handleTick();
        return true;
    }

    private void tick() {
        if (this.step * (this.step - this.runnable.count() + this.stopCount) > 0) {
            return;
        }

        this.cancel(null);
        this.handleFinish();
    }

    static final class BuilderImpl implements Counter.Builder {

        private final GamePlugin plugin;
        private long startCount;
        private long stopCount;
        private long tick;
        private TimeUnit tickUnit;

        private Consumer<Counter> startHandler;
        private Consumer<Counter> tickHandler;
        private Consumer<Counter> finishHandler;
        private Consumer<Counter> cancelHandler;

        BuilderImpl(final GamePlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public Counter.@NotNull Builder startCount(final long startCount) {
            this.startCount = startCount;
            return this;
        }

        @Override
        public Counter.@NotNull Builder stopCount(final long stopCount) {
            this.stopCount = stopCount;
            return this;
        }

        @Override
        public Counter.@NotNull Builder tick(final long tick, @NotNull final TimeUnit tickUnit) {
            this.tick = tick;
            this.tickUnit = tickUnit;
            return this;
        }

        @Override
        public Counter.@NotNull Builder startCallback(final Consumer<Counter> startCallback) {
            this.startHandler = startCallback;
            return this;
        }

        @Override
        public Counter.@NotNull Builder tickCallback(final Consumer<Counter> tickCallback) {
            this.tickHandler = tickCallback;
            return this;
        }

        @Override
        public Counter.@NotNull Builder finishCallback(final Consumer<Counter> finishCallback) {
            this.finishHandler = finishCallback;
            return this;
        }

        @Override
        public Counter.@NotNull Builder cancelCallback(final Consumer<Counter> cancelCallback) {
            this.cancelHandler = cancelCallback;
            return this;
        }

        @Override
        public @NotNull Counter build() {
            return new CounterImpl(this);
        }
    }
}
