package de.drolpi.gamecore;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.api.player.GamePlayerHandlerImpl;
import de.drolpi.gamecore.internal.task.LifeCycle;
import de.drolpi.gamecore.internal.task.TaskHandler;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.internal.config.ConfigLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;

public abstract non-sealed class AbstractGamePlugin extends JavaPlugin implements GamePlugin {

    private final TaskHandler taskHandler = TaskHandler.create(this);

    @Inject
    private Injector injector;

    @Override
    public final void onLoad() {

    }

    @Override
    public final void onEnable() {
        System.out.println(injector);
        this.taskHandler.fireTasks(LifeCycle.STARTED);
    }

    @Override
    public final void onDisable() {
        this.taskHandler.fireTasks(LifeCycle.STOPPED);
    }

    public final Injector injector() {
        return this.injector;
    }

    public final TaskHandler taskHandler() {
        return this.taskHandler;
    }
}
