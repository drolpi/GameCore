package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.counter.HandlerType;
import de.drolpi.gamecore.api.phase.Phase;

import java.util.function.Consumer;

public abstract class AbstractCounterHandlerConfigFeature extends AbstractCounterHandlerFeature {

    @Expose
    private boolean onStart = false;
    @Expose
    private boolean onTick = true;
    @Expose
    private boolean onFinish = true;
    @Expose
    private boolean onCancel = false;
    @Expose
    private int[] ticks = new int[]{60, 50, 40, 30, 20, 15, 10, 5, 4, 3, 2, 1};

    public AbstractCounterHandlerConfigFeature(Phase phase) {
        super(phase);
    }

    @Override
    public void enable() {
        this.changeHandler(this.onStart, HandlerType.START, this.startHandler, true);
        this.changeHandler(this.onTick, HandlerType.TICK, this.tickHandler, true);
        this.changeHandler(this.onFinish, HandlerType.FINISH, this.finishHandler, true);
        this.changeHandler(this.onCancel, HandlerType.CANCEL, this.cancelHandler, true);
    }

    @Override
    public void disable() {
        this.changeHandler(this.onStart, HandlerType.START, this.startHandler, false);
        this.changeHandler(this.onTick, HandlerType.TICK, this.tickHandler, false);
        this.changeHandler(this.onFinish, HandlerType.FINISH, this.finishHandler, false);
        this.changeHandler(this.onCancel, HandlerType.CANCEL, this.cancelHandler, false);
    }

    public void setOnStart(boolean onStart) {
        this.onStart = onStart;
    }

    public void setOnTick(boolean onTick) {
        this.onTick = onTick;
    }

    public void setOnFinish(boolean onFinish) {
        this.onFinish = onFinish;
    }

    public void setOnCancel(boolean onCancel) {
        this.onCancel = onCancel;
    }

    protected boolean shouldTick(long tick) {
        for (int i : this.ticks) {
            if (i != tick) {
                continue;
            }

            return true;
        }

        return false;
    }

    private void changeHandler(boolean state, HandlerType type, Consumer<Counter> handler, boolean register) {
        if (!state) {
            return;
        }

        if (register) {
            this.counterFeature.registerHandler(type, handler);
            return;
        }

        this.counterFeature.unregisterHandler(type, handler);
    }
}
