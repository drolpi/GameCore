package net.bote.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.event.GameJoinEvent;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.phase.Phase;
import net.bote.gamecore.api.player.GamePlayer;
import net.bote.gamecore.components.world.map.GameMap;
import net.bote.gamecore.components.world.map.LocationDefinition;
import net.bote.gamecore.components.world.map.LocationsDefinition;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnFeature extends AbstractFeature {

    private final GamePlugin plugin;
    private final Game game;
    private final MapFeature mapFeature;

    @Expose
    private boolean isRespawn = true;
    @Expose
    private boolean isInitialSpawn = true;

    private List<Location> locations;

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

        if (!this.isInitialSpawn) {
            return;
        }

        for (GamePlayer gamePlayer : this.game.allPlayers()) {
            gamePlayer.player().teleport(this.spawn(gamePlayer));
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        event.gamePlayer().player().teleport(this.spawn(event.gamePlayer()));
    }

    public Location spawn(GamePlayer player) {
        return this.locations.remove(ThreadLocalRandom.current().nextInt(this.locations.size()));
    }
}
