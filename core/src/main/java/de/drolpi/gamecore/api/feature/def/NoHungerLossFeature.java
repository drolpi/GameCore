package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class NoHungerLossFeature extends AbstractFeature {

    private final Game game;

    @Inject
    public NoHungerLossFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {
        for (GamePlayer player : this.game.players()) {
            player.player().setFoodLevel(20);
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        event.gamePlayer().player().setFoodLevel(20);
    }

    @EventHandler
    public void handleHunger(FoodLevelChangeEvent event) {
        if(!this.game.isParticipating(event.getEntity().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
}
