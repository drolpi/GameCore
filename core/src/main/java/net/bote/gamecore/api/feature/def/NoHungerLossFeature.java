package net.bote.gamecore.api.feature.def;

import com.google.inject.Inject;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.event.GameJoinEvent;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
