package de.drolpi.gamecore.api.counter.count;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

final class CountingRunnableImpl extends AbstractCountingRunnable {

    private CountingRunnableImpl(@NotNull final CountingRunnableImpl.BuilderImpl builder) {
        super(builder.step, builder.initial, builder.condition, builder.callback);
    }

    static final class BuilderImpl implements CountingRunnable.Builder {

        private int step = 1;
        private long initial = 0;
        private Supplier<Boolean> condition;
        private Runnable callback;

        BuilderImpl() {

        }

        public @NotNull CountingRunnableImpl.BuilderImpl step(final int step) {
            this.step = step;
            return this;
        }

        public @NotNull CountingRunnableImpl.BuilderImpl initialCount(final long initial) {
            this.initial = initial;
            return this;
        }

        public @NotNull CountingRunnableImpl.BuilderImpl condition(final Supplier<Boolean> condition) {
            this.condition = condition;
            return this;
        }

        public @NotNull CountingRunnableImpl.BuilderImpl callback(final Runnable callback) {
            this.callback = callback;
            return this;
        }

        @Override
        public @NotNull CountingRunnableImpl build() {
            return new CountingRunnableImpl(this);
        }
    }
}
