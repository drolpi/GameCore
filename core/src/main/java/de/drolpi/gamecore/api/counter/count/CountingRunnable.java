package de.drolpi.gamecore.api.counter.count;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface CountingRunnable extends Runnable {

    static @NotNull Builder builder() {
        return new CountingRunnableImpl.BuilderImpl();
    }

    long count();

    void count(long count);

    interface Builder {

        @NotNull Builder step(int step);

        @NotNull Builder initialCount(long initial);

        @NotNull Builder condition(Supplier<Boolean> condition);

        @NotNull Builder callback(Runnable callback);

        @NotNull CountingRunnable build();

    }
}
