package net.bote.gamecore.api.feature.def;

import com.google.inject.Inject;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import org.bukkit.event.EventHandler;

public class NoItemPickupFeature extends AbstractFeature {

    private final Game game;

    @Inject
    public NoItemPickupFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(PlayerPickItemEvent event) {
        if(!this.game.isParticipating(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);
    }
}
