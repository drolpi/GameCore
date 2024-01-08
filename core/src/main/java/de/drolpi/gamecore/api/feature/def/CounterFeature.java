package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.counter.HandlerType;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.feature.AbstractFeature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CounterFeature extends AbstractFeature {

    private final GamePlugin plugin;
    private final Game game;
    private final Map<HandlerType, Set<Consumer<Counter>>> handlers;
    private final Consumer<Counter> finishHandler;

    @Expose
    private int startCount = 60;
    @Expose
    private int stopCount = 0;
    @Expose
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    @Expose
    private boolean autoStart = true;
    @Expose
    private boolean autoStop = true;

    private Counter counter;

    @Inject
    protected CounterFeature(GamePlugin plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
        this.handlers = new ConcurrentHashMap<>();
        this.finishHandler = this::finish;
    }

    @Override
    public void enable() {
        if (this.counter != null) {
            throw new RuntimeException();
        }

        this.counter = Counter
            .builder(this.plugin)
            .startCount(this.startCount)
            .stopCount(this.stopCount)
            .tick(1, this.timeUnit)
            .startCallback(c -> this.call(HandlerType.START, c))
            .tickCallback(c -> this.call(HandlerType.TICk, c))
            .cancelCallback(c -> this.call(HandlerType.CANCEL, c))
            .finishCallback(c -> this.call(HandlerType.FINISH, c))
            .build();
        this.registerHandler(HandlerType.FINISH, this.finishHandler);

        if (!this.autoStart) {
            return;
        }
        this.counter.start();
    }

    @Override
    public void disable() {
        this.counter.stop();
        this.counter = null;
        this.unregisterHandler(HandlerType.FINISH, this.finishHandler);
    }

    private void finish(Counter counter) {
        if(!this.autoStop) {
            return;
        }
        this.game.nextPhase();
    }

    public Counter counter() {
        return this.counter;
    }

    protected void call(HandlerType handlerType, Counter counter) {
        this.handlers.computeIfPresent(handlerType, (handlerType1, handlers) -> {
            for (Consumer<Counter> handler : new HashSet<>(handlers)) {
                handler.accept(counter);
            }
            return handlers;
        });
    }

    public void registerHandler(HandlerType handlerType, Consumer<Counter> handler) {
        final Set<Consumer<Counter>> handlers = this.handlers.computeIfAbsent(handlerType, ht -> new HashSet<>());
        handlers.add(handler);
    }

    public void unregisterHandler(HandlerType handlerType, Consumer<Counter> handler) {
        final Set<Consumer<Counter>> handlers = this.handlers.computeIfAbsent(handlerType, ht -> new HashSet<>());
        handlers.remove(handler);
    }

    public void startCount(int startCount) {
        this.startCount = startCount;
    }

    public int startCount() {
        return this.startCount;
    }

    public void stopCount(int stopCount) {
        this.stopCount = stopCount;
    }

    public int stopCount() {
        return this.stopCount;
    }

    public TimeUnit timeUnit() {
        return this.timeUnit;
    }

    public void timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public boolean autoStart() {
        return this.autoStart;
    }

    public void autoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public boolean autoStop() {
        return this.autoStop;
    }

    public void autoStop(boolean autoStop) {
        this.autoStop = autoStop;
    }
}
