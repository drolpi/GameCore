package net.bote.gamecore.api.eventbus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class EventListenerImpl<T> implements EventListener<T> {

    private final Class<T> type;
    private final List<Predicate<T>> conditions;
    private final Consumer<T> handler;

    public EventListenerImpl(Class<T> type, List<Predicate<T>> conditions, Consumer<T> handler) {
        this.type = type;
        this.conditions = conditions;
        this.handler = handler;
    }

    @Override
    public @NotNull Class<T> eventType() {
        return this.type;
    }

    @Override
    public void handle(@NotNull T event) {
        if (!this.conditions.isEmpty()) {
            for (Predicate<T> condition : this.conditions) {
                if (!condition.test(event))
                    return;
            }
        }

        if (this.handler == null)
            return;
        this.handler.accept(event);
    }

    static final class BuilderImpl<T> implements EventListener.Builder<T> {

        private final Class<T> type;
        private final List<Predicate<T>> conditions;
        private Consumer<T> handler;

        public BuilderImpl(Class<T> type) {
            this.type = type;
            this.conditions = new ArrayList<>();
        }

        @Override
        public EventListener.@NotNull Builder<T> condition(@NotNull Predicate<T> condition) {
            this.conditions.add(condition);
            return this;
        }

        @Override
        public EventListener.@NotNull Builder<T> handler(@NotNull Consumer<T> handler) {
            this.handler = handler;
            return this;
        }

        @Override
        public @NotNull EventListener<T> build() {
            return new EventListenerImpl<>(this.type, new ArrayList<>(this.conditions), this.handler);
        }
    }
}
