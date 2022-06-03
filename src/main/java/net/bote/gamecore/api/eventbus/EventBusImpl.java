package net.bote.gamecore.api.eventbus;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes"})
final class EventBusImpl implements EventBus {

    private final Set<Map.Entry<Class, EventListener>> listeners;

    EventBusImpl() {
        this.listeners = new CopyOnWriteArraySet<>();
    }

    @Override
    public void register(@NotNull EventListener<?> listener) {
        this.listeners.add(new AbstractMap.SimpleEntry<>(listener.eventType(), listener));
    }

    @Override
    public void unregister(@NotNull EventListener<?> listener) {
        this.listeners.removeIf(entry -> entry.getValue().equals(listener));
    }

    @Override
    public void unregisterIf(@NotNull Predicate<EventListener<?>> predicate) {
        this.listeners.removeIf(entry -> predicate.test(entry.getValue()));
    }

    @Override
    public boolean listening(@NotNull EventListener<?> listener) {
        for (Map.Entry<Class, EventListener> entry : this.listeners) {
            if (entry.getValue().equals(listener))
                return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void call(@NotNull Object event) {
        for (Map.Entry<Class, EventListener> entry : this.listeners) {
            if (!entry.getKey().isAssignableFrom(event.getClass()))
                continue;
            entry.getValue().handle(event);
        }
    }
}
