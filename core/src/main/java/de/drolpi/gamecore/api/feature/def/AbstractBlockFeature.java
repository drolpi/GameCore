package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

import java.util.Arrays;

public abstract class AbstractBlockFeature extends AbstractFeature {

    @Expose
    private Material[] whitelist = new Material[0];
    @Expose
    private Material[] blacklist = new Material[0];

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    protected void handle(BlockEvent event, Cancellable cancellable) {
        if (this.blacklist.length != 0) {
            if (Arrays.stream(this.blacklist).anyMatch(m -> m.equals(event.getBlock().getType()))) {
                cancellable.setCancelled(true);
            }
        } else if (this.whitelist.length != 0) {
            if (Arrays.stream(this.whitelist).noneMatch(m -> m.equals(event.getBlock().getType()))) {
                cancellable.setCancelled(true);
            }
        } else {
            cancellable.setCancelled(true);
        }
    }

    public void setBlacklist(Material... blacklist) {
        this.blacklist = blacklist;
    }

    public void setWhitelist(Material... whitelist) {
        this.whitelist = whitelist;
    }
}
