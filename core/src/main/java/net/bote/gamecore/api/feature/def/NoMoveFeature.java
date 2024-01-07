package net.bote.gamecore.api.feature.def;

import com.google.inject.Inject;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.feature.FeatureInfo;
import net.bote.gamecore.api.game.Game;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

@FeatureInfo(name = "NoMoveFeature")
public class NoMoveFeature extends AbstractFeature {

    private final Game game;

    @Inject
    public NoMoveFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(PlayerMoveEvent event) {
        if(!this.game.isPlaying(event.getPlayer().getUniqueId())) {
            return;
        }

        final Location from = event.getFrom();
        final Location to = new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), event.getTo().getYaw(), event.getTo().getPitch());

        event.setTo(to);
    }
}
