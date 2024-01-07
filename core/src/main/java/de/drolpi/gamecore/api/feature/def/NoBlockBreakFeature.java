package de.drolpi.gamecore.api.feature.def;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class NoBlockBreakFeature extends AbstractBlockFeature {

    @EventHandler
    public void handle(BlockBreakEvent event) {
        this.handle(event, event);
    }
}
