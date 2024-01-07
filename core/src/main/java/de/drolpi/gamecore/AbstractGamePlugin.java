package de.drolpi.gamecore;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.api.task.LifeCycle;
import de.drolpi.gamecore.api.task.TaskHandler;
import de.drolpi.gamecore.components.localization.MessageFormatTranslationProvider;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageComponentRenderer;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageTranslator;
import de.drolpi.gamecore.components.localization.bundle.FileJsonResourceLoader;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.components.world.map.LocationDefinition;
import de.drolpi.gamecore.components.world.map.LocationsDefinition;
import de.drolpi.gamecore.components.world.map.storage.JsonMapInfoStorage;
import de.drolpi.gamecore.internal.listener.StartUpListener;
import de.drolpi.gamecore.api.game.GameController;
import de.drolpi.gamecore.api.player.GamePlayerHandlerImpl;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.internal.config.ConfigLoader;
import de.drolpi.gamecore.internal.listener.GameListener;
import de.drolpi.gamecore.internal.listener.GamePlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class AbstractGamePlugin extends JavaPlugin implements GamePlugin {

    private final TaskHandler taskHandler = TaskHandler.create(this);
    private Injector injector;

    @Inject
    private ConfigLoader configLoader;
    @Inject
    private GameControllerImpl gameController;
    @Inject
    private GamePlayerHandlerImpl gamePlayerHandler;
    @Inject
    private WorldHandler worldHandler;

    @Override
    public final void onLoad() {

    }

    @Override
    public final void onEnable() {
        this.injector = Guice.createInjector(new GameCoreModule(this, this.getDataFolder()));
        this.injector.injectMembers(this);

        try {
            Files.createDirectories(this.getDataFolder().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.registerListener();

        this.taskHandler.fireTasks(LifeCycle.STARTED);

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
        this.worldHandler.saveMap(future);

        this.gameController.startDefaultGame();
    }

    @Override
    public final void onDisable() {
        this.taskHandler.fireTasks(LifeCycle.STOPPED);

        this.worldHandler.disable();
    }

    private void registerListener() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new StartUpListener(), this);
        pluginManager.registerEvents(this.injector.getInstance(GamePlayerListener.class), this);
        pluginManager.registerEvents(this.injector.getInstance(GameListener.class), this);
    }

    @Override
    public final @NotNull GameController gameController() {
        return this.gameController;
    }
}
