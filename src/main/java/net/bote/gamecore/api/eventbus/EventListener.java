package net.bote.gamecore.api.eventbus;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface EventListener<T> {

    static <T> @NotNull Builder<T> builder(@NotNull Class<T> type) {
        return new EventListenerImpl.BuilderImpl<>(type);
    }

    static <T> @NotNull EventListener<T> of(@NotNull Class<T> type, @NotNull Consumer<T> handler) {
        return EventListener.builder(type).handler(handler).build();
    }

    @NotNull Class<T> eventType();

    void handle(@NotNull T event);

    interface Builder<T>  {

        @NotNull Builder<T> condition(@NotNull Predicate<T> condition);

        @NotNull Builder<T> handler(@NotNull Consumer<T> handler);

        @NotNull EventListener<T> build();
    }

}
