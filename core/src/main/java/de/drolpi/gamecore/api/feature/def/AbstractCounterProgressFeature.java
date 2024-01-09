package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public abstract class AbstractCounterProgressFeature extends AbstractCounterHandlerFeature {

    private final Game game;

    @Inject
    public AbstractCounterProgressFeature(Game game, Phase phase) {
        super(phase);
        this.game = game;
    }

    @Override
    public void enable() {
        super.enable();
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        Player player = event.gamePlayer().player();
        Counter counter = this.counterFeature.counter();
        if (counter.isRunning() && counter.isPaused()) {
            return;
        }

        this.reset(player, counter);
    }

    @Override
    protected void start(Counter counter) {

    }

    @Override
    protected void tick(Counter counter) {
        for (final GamePlayer player : this.game.allPlayers()) {
            this.set(player.player(), (int) counter.currentCount(), this.progress(counter));
        }
    }

    @Override
    protected void cancel(Counter counter) {
        for (final GamePlayer player : this.game.allPlayers()) {
            this.reset(player.player(), counter);
        }
    }

    @Override
    protected void finish(Counter counter) {

    }

    protected float progress(Counter counter) {
        return (float) (counter.currentCount() - this.lowerBound(counter)) / (this.upperBound(counter) - this.lowerBound(counter));
    }

    protected long upperBound(Counter counter) {
        return Math.max(counter.startCount(), counter.stopCount());
    }

    protected long lowerBound(Counter counter) {
        return Math.min(counter.startCount(), counter.stopCount());
    }

    protected void reset(Player player, Counter counter) {
        this.set(player, counter.startCount(), counter.startCount() > counter.stopCount() ? 1F : 0F);
    }

    protected abstract void set(Player player, long count, float progress);
}
