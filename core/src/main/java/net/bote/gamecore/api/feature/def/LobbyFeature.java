package net.bote.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.api.counter.Counter;
import net.bote.gamecore.api.event.GameJoinEvent;
import net.bote.gamecore.api.event.GamePostLeaveEvent;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.phase.Phase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class LobbyFeature extends AbstractFeature {

    private final Game game;
    private final CounterFeature counterFeature;

    @Expose
    private int playersToStart = 1;
    @Expose
    private boolean shouldReduce = true;
    @Expose
    private int playersToReduce = 2;
    @Expose
    private int reduceTime = 20;

    @Inject
    public LobbyFeature(Game game, Phase phase) {
        this.game = game;
        this.counterFeature = phase.feature(CounterFeature.class);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(GameJoinEvent event) {
        final Counter counter = this.counterFeature.counter();
        final int size = this.game.players().size();
        if (size >= this.playersToStart) {
            if (counter.isPaused()) {
                counter.resume();
            } else if (!counter.isRunning()) {
                counter.start();
            }
        }

        if (this.shouldReduce && size >= this.playersToReduce) {
            if (counter.currentCount() > this.reduceTime) {
                counter.currentCount(this.reduceTime);
            }
        }
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        final Counter counter = this.counterFeature.counter();
        final int size = this.game.players().size();
        if (size < this.playersToStart) {
            if (!counter.isPaused() && counter.isRunning()) {
                counter.stop();
            }
        }
    }
}
