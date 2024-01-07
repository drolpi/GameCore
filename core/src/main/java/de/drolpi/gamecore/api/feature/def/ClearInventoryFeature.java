package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import org.bukkit.event.EventHandler;

public class ClearInventoryFeature extends AbstractFeature {

    private final Game game;

    @Inject
    public ClearInventoryFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {
        for (GamePlayer gamePlayer : this.game.allPlayers()) {
            gamePlayer.player().getInventory().clear();
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        event.gamePlayer().player().getInventory().clear();
    }
}
