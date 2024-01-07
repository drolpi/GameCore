package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import io.papermc.paper.event.player.PlayerPickItemEvent;
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
