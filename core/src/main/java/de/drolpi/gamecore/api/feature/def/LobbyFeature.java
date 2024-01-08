package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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
        final GamePlayer joinedPlayer = event.gamePlayer();
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.sendMessage(Component.translatable("lobby.player_join"), Placeholder.component("playername", Component.text(joinedPlayer.player().getName())));
        }

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
        final GamePlayer quitedPlayer = event.gamePlayer();
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.sendMessage(Component.translatable("lobby.player_quit"), Placeholder.component("playername", Component.text(quitedPlayer.player().getName())));
        }

        final Counter counter = this.counterFeature.counter();
        final int size = this.game.players().size();
        if (size < this.playersToStart) {
            if (!counter.isPaused() && counter.isRunning()) {
                counter.stop();
            }
        }
    }
}
