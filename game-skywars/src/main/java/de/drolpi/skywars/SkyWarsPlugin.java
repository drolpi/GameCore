package de.drolpi.skywars;

import de.drolpi.gamecore.AbstractGamePlugin;
import de.drolpi.gamecore.PluginInfo;
import de.drolpi.gamecore.api.game.GameController;
import de.drolpi.gamecore.api.task.LifeCycle;
import de.drolpi.gamecore.api.task.Task;

@PluginInfo(name = "SkyWars", description = "The SkyWars plugin", version = "1.0.0-SNAPSHOT", authors = {"dasdrolpi"})
public final class SkyWarsPlugin extends AbstractGamePlugin {

    @Task(event = LifeCycle.STARTED, order = 1)
    public void enable() {
        GameController gameController = this.gameController();
        gameController.createGameType(SkyWarsGame.class);
    }

    @Task(event = LifeCycle.STOPPED, order = 1)
    public void disable() {

    }
}
