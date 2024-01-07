package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class NoBlockPlaceFeature extends AbstractBlockFeature {

    private final Game game;

    @Inject
    public NoBlockPlaceFeature(Game game) {
        this.game = game;
    }

    @EventHandler
    public void handle(BlockPlaceEvent event) {
        if(!this.game.isParticipating(event.getPlayer().getUniqueId())) {
            return;
        }
        this.handle(event, event);
    }
}
