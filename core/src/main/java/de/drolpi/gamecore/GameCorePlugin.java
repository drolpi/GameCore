package de.drolpi.gamecore;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.internal.listener.GameListener;
import de.drolpi.gamecore.internal.listener.GamePlayerListener;
import de.drolpi.gamecore.internal.listener.StartUpListener;
import de.drolpi.gamecore.internal.task.LifeCycle;
import de.drolpi.gamecore.internal.task.TaskEntry;
import de.drolpi.gamecore.internal.task.TaskHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public final class GameCorePlugin extends JavaPlugin {

    private Injector injector;

    @Inject
    private GameControllerImpl gameController;
    @Inject
    private WorldHandler worldHandler;

    private boolean started = false;

    @Override
    public void onEnable() {
        final GameCoreModule module = new GameCoreModule(this, this.getDataFolder());
        this.injector = Guice.createInjector(module);
        this.injector.injectMembers(this);

        System.out.println("asd" + gameController);

        try {
            Files.createDirectories(this.getDataFolder().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final PluginManager pluginManager = this.getServer().getPluginManager();
        final AtomicInteger gamePlugins = new AtomicInteger();
        final AtomicInteger startedPlugins = new AtomicInteger();
        for (final Plugin plugin : pluginManager.getPlugins()) {
            System.out.println("Trying to get control over the plugin: " + plugin.getName());
            if (!(plugin instanceof AbstractGamePlugin abstractGamePlugin)) {
                continue;
            }

            gamePlugins.incrementAndGet();

            final Injector pluginInjector = this.injector.createChildInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    this.bind(GamePlugin.class).to(AbstractGamePlugin.class);
                    this.bind(AbstractGamePlugin.class).toInstance(abstractGamePlugin);
                }
            });
            pluginInjector.injectMembers(abstractGamePlugin);

            final TaskHandler taskHandler = abstractGamePlugin.taskHandler();
            taskHandler.registerHandler(new TaskEntry(Integer.MAX_VALUE, LifeCycle.STARTED, () -> {
                System.out.println("Got control over the plugin: " + plugin.getName());
                if (startedPlugins.incrementAndGet() != gamePlugins.get()) {
                    return;
                }

                System.out.println("Got control over " + startedPlugins.get() + " plugins");
                GameCorePlugin.this.onPluginsEnabled();
            }));
        }

        this.registerListener();
    }

    public void onPluginsEnabled() {
        this.gameController.startDefaultGame();
        this.started = true;
    }

    @Override
    public void onDisable() {
        this.worldHandler.disable();
    }

    private void registerListener() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(this.injector.getInstance(StartUpListener.class), this);
        pluginManager.registerEvents(this.injector.getInstance(GamePlayerListener.class), this);
        pluginManager.registerEvents(this.injector.getInstance(GameListener.class), this);
    }

    public boolean started() {
        return this.started;
    }
}
