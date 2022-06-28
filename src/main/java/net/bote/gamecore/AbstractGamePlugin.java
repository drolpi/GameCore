package net.bote.gamecore;

import net.bote.gamecore.api.game.GameController;
import net.bote.gamecore.api.task.LifeCycle;
import net.bote.gamecore.api.task.TaskHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractGamePlugin extends JavaPlugin implements GamePlugin {

    private final TaskHandler taskHandler = TaskHandler.create(this);
    private GameController gameController;

    @Override
    public final void onLoad() {

    }

    @Override
    public final void onEnable() {
        this.gameController = new GameControllerImpl();
        this.registerListeners();

        this.taskHandler.fireTasks(LifeCycle.STARTED);
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();


    }

    @Override
    public final void onDisable() {
        this.taskHandler.fireTasks(LifeCycle.STOPPED);
    }

    @Override
    public @NotNull final GameController gameController() {
        return this.gameController;
    }
}
