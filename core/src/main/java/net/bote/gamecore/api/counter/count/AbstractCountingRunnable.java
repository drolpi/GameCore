package net.bote.gamecore.api.counter.count;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public abstract class AbstractCountingRunnable implements CountingRunnable {

    protected final Supplier<Boolean> condition;
    protected final Runnable callback;
    protected final int step;
    protected final AtomicLong count;

    public AbstractCountingRunnable(final int step, final long count, final Supplier<Boolean> condition, final Runnable callback) {
        this.step = step;
        this.count = new AtomicLong(count);
        this.condition = condition;
        this.callback = callback;
    }

    @Override
    public void run() {
        if (this.condition.get()) {
            this.count.getAndAdd(this.step);
        }
        this.callback.run();
    }

    public int step() {
        return this.step;
    }

    public long count() {
        return this.count.get();
    }

    public void count(final long count) {
        this.count.set(count);
    }
}
