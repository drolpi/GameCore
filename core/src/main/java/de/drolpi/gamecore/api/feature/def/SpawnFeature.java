package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.components.world.map.LocationDefinition;
import de.drolpi.gamecore.components.world.map.LocationsDefinition;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class SpawnFeature extends AbstractFeature {

    private final GamePlugin plugin;
    private final Game game;
    private final MapFeature mapFeature;

    @Expose
    private boolean isRespawn = true;
    @Expose
    private boolean isInitialSpawn = true;

    private List<Location> locations;
    private boolean shouldRemove;

    @Inject
    public SpawnFeature(GamePlugin plugin, Game game, Phase phase) {
        this.plugin = plugin;
        this.game = game;
        this.mapFeature = phase.feature(MapFeature.class);
    }

    @Override
    public void enable() {
        this.locations = new ArrayList<>();

        GameMap gameMap = this.mapFeature.gameMap();
        LocationsDefinition definition = gameMap.definition("Spawns");

        for (LocationDefinition location : definition.locations()) {
            this.locations.add(location.toLocation(this.mapFeature.world()));
        }
        this.shouldRemove = this.locations.size() > 1;

        if (!this.isInitialSpawn) {
            return;
        }

        for (GamePlayer gamePlayer : this.game.allPlayers()) {
            gamePlayer.player().teleport(this.spawn());
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        event.gamePlayer().player().teleport(this.spawn());
    }

    public Location spawn() {
        int location = ThreadLocalRandom.current().nextInt(this.locations.size());
        return this.shouldRemove ? this.locations.remove(location) : this.locations.get(location);
    }
}
