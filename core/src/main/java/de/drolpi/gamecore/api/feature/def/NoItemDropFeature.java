package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class NoItemDropFeature extends AbstractFeature {

    private final Game game;

    @Inject
    public NoItemDropFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        if(!this.game.isParticipating(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);
    }
}
