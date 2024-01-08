package de.drolpi.gamecore.api.feature.def;

import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.phase.Phase;

public abstract class AbstractKeyCounterHandlerFeature extends AbstractCounterHandlerConfigFeature {

    private final Phase phase;

    public AbstractKeyCounterHandlerFeature(Phase phase) {
        super(phase);
        this.phase = phase;
    }

    protected abstract void perform(Counter counter, String preKey);

    @Override
    protected void start(Counter counter) {
        this.perform(counter, this.phase.key() + "counter_start");
    }

    @Override
    protected void tick(Counter counter) {
        if (!this.shouldTick(counter.currentCount())) {
            return;
        }

        this.perform(counter, this.phase.key() + "counter_tick");
    }

    @Override
    protected void cancel(Counter counter) {
        this.perform(counter, this.phase.key() + "counter_cancel");
    }

    @Override
    protected void finish(Counter counter) {
        this.perform(counter, this.phase.key() + "counter_finish");
    }
}
