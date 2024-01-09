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
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class SpawnFeature extends AbstractFeature {

    //TODO: Implement Respawn

    private final GamePlugin plugin;
    private final Game game;
    private final MapFeature mapFeature;
    private final List<Runnable> handlers;

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
        this.handlers = new ArrayList<>();
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

        final AtomicInteger count = new AtomicInteger();
        final List<GamePlayer> players = this.game.allPlayers();

        for (final GamePlayer gamePlayer : players) {
            gamePlayer.player().teleportAsync(this.spawn()).thenRun(() -> {
                int c = count.incrementAndGet();
                if (players.size() != c) {
                    return;
                }

                this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                    for (Runnable handler : this.handlers) {
                        handler.run();
                    }
                });
            });
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        //TODO: Maybe teleport async? Check which consequences it would have
        event.gamePlayer().player().teleport(this.spawn());
    }

    public void registerHandler(Runnable handler) {
        this.handlers.add(handler);
    }

    private Location spawn() {
        int location = ThreadLocalRandom.current().nextInt(this.locations.size());
        return this.shouldRemove ? this.locations.remove(location) : this.locations.get(location);
    }
}
