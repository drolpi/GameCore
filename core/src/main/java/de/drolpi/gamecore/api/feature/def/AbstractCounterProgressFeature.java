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

public abstract class AbstractCounterProgressFeature extends AbstractCounterHandlerFeature {

    private final Game game;

    private boolean countUp;

    @Inject
    public AbstractCounterProgressFeature(Game game, Phase phase) {
        super(phase);
        this.game = game;
    }

    @Override
    public void enable() {
        super.enable();
        this.countUp = this.counterFeature.stopCount() > this.counterFeature.startCount();
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
            this.setStart(player.player(), counter);
        }
    }

    @Override
    protected void finish(Counter counter) {

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
