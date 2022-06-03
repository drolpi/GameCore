package net.bote.gamecore.api.eventbus;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface EventBus {

    static @NotNull EventBus create() {
        return new EventBusImpl();
    }

    void register(@NotNull EventListener<?> listener);

    default <T> void register(@NotNull Class<T> type, @NotNull Consumer<T> handler) {
        this.register(EventListener.of(type, handler));
    }

    void unregister(@NotNull EventListener<?> listener);

    void unregisterIf(@NotNull Predicate<EventListener<?>> predicate);

    boolean listening(@NotNull EventListener<?> type);

    void call(@NotNull Object event);

}
