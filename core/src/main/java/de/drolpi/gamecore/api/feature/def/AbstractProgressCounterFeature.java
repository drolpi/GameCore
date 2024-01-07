package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.counter.HandlerType;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.function.Consumer;

public abstract class AbstractProgressCounterFeature extends AbstractFeature {

    protected final CounterFeature counterFeature;
    private final Game game;
    private final Consumer<Counter> startHandler;
    private final Consumer<Counter> tickHandler;
    private final Consumer<Counter> cancelHandler;

    private boolean countUp;

    @Inject
    public AbstractProgressCounterFeature(Game game, Phase phase) {
        this.game = game;
        this.counterFeature = phase.feature(CounterFeature.class);
        this.startHandler = this::start;
        this.tickHandler = this::tick;
        this.cancelHandler = this::cancel;
    }

    @Override
    public void enable() {
        this.countUp = this.counterFeature.stopCount() > this.counterFeature.startCount();
        this.counterFeature.registerHandler(HandlerType.START, this.startHandler);
        this.counterFeature.registerHandler(HandlerType.TICk, this.tickHandler);
        this.counterFeature.registerHandler(HandlerType.CANCEL, this.cancelHandler);
    }

    @Override
    public void disable() {
        this.counterFeature.unregisterHandler(HandlerType.START, this.startHandler);
        this.counterFeature.unregisterHandler(HandlerType.TICk, this.tickHandler);
        this.counterFeature.unregisterHandler(HandlerType.CANCEL, this.cancelHandler);
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        Player player = event.gamePlayer().player();
        Counter counter = this.counterFeature.counter();
        if (counter.isRunning() && counter.isPaused()) {
            return;
        }

        this.setStart(player, counter);
    }

    protected void start(Counter counter) {

    }

    protected void tick(Counter counter) {
        for (final GamePlayer player : this.game.allPlayers()) {
            this.set(player.player(), (int) counter.currentCount(), this.progress(counter));
        }
    }

    protected void cancel(Counter counter) {
        for (final GamePlayer player : this.game.allPlayers()) {
            this.setStart(player.player(), counter);
        }
    }

    protected float progress(Counter counter) {
        return counter.currentCount() / (this.startCount(counter) + 0.0F);
    }

    protected long startCount(Counter counter) {
        return this.countUp ? counter.stopCount() : counter.startCount();
    }

    protected void setStart(Player player, Counter counter) {
        this.set(player, (int) this.startCount(counter), this.countUp ? 0 : 1);
    }

    protected abstract void set(Player player, int count, float progress);
}
