package de.drolpi.gamecore.api.feature.def;

import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.counter.HandlerType;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.phase.Phase;

import java.util.function.Consumer;

public abstract class AbstractCounterHandlerFeature extends AbstractFeature {

    protected final CounterFeature counterFeature;
    protected final Consumer<Counter> startHandler;
    protected final Consumer<Counter> tickHandler;
    protected final Consumer<Counter> cancelHandler;
    protected final Consumer<Counter> finishHandler;

    public AbstractCounterHandlerFeature(Phase phase) {
        this.counterFeature = phase.feature(CounterFeature.class);
        this.startHandler = this::start;
        this.tickHandler = this::tick;
        this.cancelHandler = this::cancel;
        this.finishHandler = this::finish;
    }

    @Override
    public void enable() {
        this.counterFeature.registerHandler(HandlerType.START, this.startHandler);
        this.counterFeature.registerHandler(HandlerType.TICK, this.tickHandler);
        this.counterFeature.registerHandler(HandlerType.CANCEL, this.cancelHandler);
        this.counterFeature.registerHandler(HandlerType.FINISH, this.finishHandler);
    }

    @Override
    public void disable() {
        this.counterFeature.unregisterHandler(HandlerType.START, this.startHandler);
        this.counterFeature.unregisterHandler(HandlerType.TICK, this.tickHandler);
        this.counterFeature.unregisterHandler(HandlerType.CANCEL, this.cancelHandler);
        this.counterFeature.unregisterHandler(HandlerType.FINISH, this.finishHandler);
    }

    protected abstract void start(Counter counter);

    protected abstract void tick(Counter counter);

    protected abstract void cancel(Counter counter);

    protected abstract void finish(Counter counter);
}
