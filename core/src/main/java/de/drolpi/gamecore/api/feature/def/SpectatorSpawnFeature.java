package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.components.world.map.LocationDefinition;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpectatorSpawnFeature extends AbstractFeature {

    private final Game game;
    private final MapFeature mapFeature;
    private Location location;

    @Inject
    public SpectatorSpawnFeature(Game game, Phase phase) {
        this.game = game;
        this.mapFeature = phase.feature(MapFeature.class);
    }

    @Override
    public void enable() {
        GameMap gameMap = this.mapFeature.gameMap();
        LocationDefinition definition = gameMap.definition("SpectatorSpawn");

        this.location = definition.toLocation(this.mapFeature.world());
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        final GamePlayer player = event.gamePlayer();
        if (!this.game.isSpectating(player.uniqueId())) {
            return;
        }

        player.player().teleport(this.location);
    }

    //TODO: Remove when player gets automatically rejoined as spectator
    @EventHandler
    public void handle(PlayerRespawnEvent event) {
        if (!this.game.isSpectating(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setRespawnLocation(this.location);
    }
}
