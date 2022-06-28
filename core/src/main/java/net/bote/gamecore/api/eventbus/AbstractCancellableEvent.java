package net.bote.gamecore.api.eventbus;

public abstract class AbstractCancellableEvent implements CancellableEvent {

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
