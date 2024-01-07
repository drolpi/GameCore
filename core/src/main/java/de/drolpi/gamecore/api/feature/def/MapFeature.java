package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.components.world.map.MapGameData;
import org.bukkit.World;

public class MapFeature extends AbstractFeature {

    private final GamePlugin plugin;
    private final Game game;
    private final WorldHandler worldHandler;
    private GameMap gameMap;
    private World world;

    @Expose
    private boolean shouldUnload = false;

    @Inject
    public MapFeature(GamePlugin plugin, Game game, WorldHandler worldHandler) {
        this.plugin = plugin;
        this.game = game;
        this.worldHandler = worldHandler;
    }

    @Override
    public void enable() {
        // we already set the map externally, no need to do anything of the following, just set the world
        if (this.gameMap != null) {
            //TODO:
            this.world = this.plugin.getServer().getWorld("");
            return;
        }

        MapGameData gameData = this.game.gameData(MapGameData.class).orElse(new MapGameData());
        String name = gameData.mapInfo.name();

        this.gameMap = this.worldHandler.loadMap(name);
        this.world = this.worldHandler.loadWorld(this.gameMap);
    }

    @Override
    public void disable() {
        //TODO: Remove player before unload
        /*
        if (this.shouldUnload) {
            this.worldHandler.unloadWorld(this.gameMap);
        }
         */
    }

    public GameMap gameMap() {
        return this.gameMap;
    }

    public World world() {
        return this.world;
    }

    public void setShouldUnload(boolean shouldUnload) {
        this.shouldUnload = shouldUnload;
    }
}
