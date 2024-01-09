package de.drolpi.skywars;

import com.google.inject.Inject;
import de.drolpi.gamecore.AbstractGamePlugin;
import de.drolpi.gamecore.PluginInfo;
import de.drolpi.gamecore.api.game.GameController;
import de.drolpi.gamecore.api.game.GameType;
import de.drolpi.gamecore.internal.task.LifeCycle;
import de.drolpi.gamecore.internal.task.Task;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.components.world.map.LocationDefinition;
import de.drolpi.gamecore.components.world.map.LocationsDefinition;

@PluginInfo(name = "SkyWars", description = "The SkyWars plugin", version = "1.0.0-SNAPSHOT", authors = {"dasdrolpi"})
public final class SkyWarsPlugin extends AbstractGamePlugin {

    @Inject
    private GameController gameController;
    @Inject
    private WorldHandler worldHandler;

    @Task(event = LifeCycle.STARTED, order = 1)
    public void enable() {
        System.out.println(this.injector());
        System.out.println(gameController);
        System.out.println(worldHandler);
        GameType skywars = this.gameController.createGameType(SkyWarsGame.class, this);

        GameMap lobby = new GameMap();
        lobby.setName("WarteLobby");
        LocationsDefinition lobbyLocationsDefinition = new LocationsDefinition();
        lobbyLocationsDefinition.locations().add(new LocationDefinition(0.5, 100, 0.5, 0, 0));
        lobby.addDefinition("Spawns", lobbyLocationsDefinition);
        this.worldHandler.saveMap(lobby);

        GameMap future = new GameMap();
        future.setName("SWFuture");
        LocationsDefinition locationsDefinition = new LocationsDefinition();
        locationsDefinition.locations().add(new LocationDefinition(-13.5, 99, 55.5, 180, 0));
        locationsDefinition.locations().add(new LocationDefinition(14.5, 99, 55.5, 180, 0));
        locationsDefinition.locations().add(new LocationDefinition(55.5, 99, 14.5, 90, 0));
        locationsDefinition.locations().add(new LocationDefinition(55.5, 99, -13.5, 90, 0));
        locationsDefinition.locations().add(new LocationDefinition(14.5, 99, -54.5, 0, 0));
        locationsDefinition.locations().add(new LocationDefinition(-13.5, 99, -54.5, 0, 0));
        locationsDefinition.locations().add(new LocationDefinition(-54.5, 99, -13.5, -90, 0));
        locationsDefinition.locations().add(new LocationDefinition(-54.5, 99, 14.5, -90, 0));

        future.addDefinition("Spawns", locationsDefinition);
        future.addDefinition("SpectatorSpawn", new LocationDefinition(0, 100, 0, 0, 0));
        this.worldHandler.saveMap(future);
    }

    @Task(event = LifeCycle.STOPPED, order = 1)
    public void disable() {

    }
}
