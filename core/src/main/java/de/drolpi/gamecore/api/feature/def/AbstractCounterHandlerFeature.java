package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.counter.HandlerType;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.phase.Phase;

import java.util.function.Consumer;

public abstract class AbstractCounterHandlerFeature extends AbstractFeature {

    protected final CounterFeature counterFeature;
    private final Consumer<Counter> startHandler;
    private final Consumer<Counter> tickHandler;
    private final Consumer<Counter> cancelHandler;
    private final Consumer<Counter> finishHandler;

    @Inject
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
        this.counterFeature.registerHandler(HandlerType.TICk, this.tickHandler);
        this.counterFeature.registerHandler(HandlerType.CANCEL, this.cancelHandler);
        this.counterFeature.registerHandler(HandlerType.FINISH, this.finishHandler);
    }

    @Override
    public void disable() {
        //TODO: Fix ConcurrentModifyException because calling this methods by an handler
        //this.counterFeature.unregisterHandler(HandlerType.START, this.startHandler);
        //this.counterFeature.unregisterHandler(HandlerType.TICk, this.tickHandler);
        //this.counterFeature.unregisterHandler(HandlerType.CANCEL, this.cancelHandler);
        //this.counterFeature.unregisterHandler(HandlerType.FINISH, this.finishHandler);
    }

    protected abstract void start(Counter counter);

    protected abstract void tick(Counter counter);

    protected abstract void cancel(Counter counter);

    protected abstract void finish(Counter counter);
}
