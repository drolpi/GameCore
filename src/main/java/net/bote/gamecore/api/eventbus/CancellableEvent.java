package net.bote.gamecore.api.eventbus;

public interface CancellableEvent {

    boolean isCancelled();

    void setCancelled(boolean cancel);
}
