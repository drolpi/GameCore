package net.bote.skywars;

import net.bote.gamecore.AbstractGamePlugin;
import net.bote.gamecore.PluginInfo;
import net.bote.gamecore.api.game.GameController;
import net.bote.gamecore.api.game.GameInstance;
import net.bote.gamecore.api.task.LifeCycle;
import net.bote.gamecore.api.task.Task;

@PluginInfo(name = "SkyWars", description = "The SkyWars plugin", version = "3.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
public final class SkyWarsPlugin extends AbstractGamePlugin {

    @Task(event = LifeCycle.STARTED, order = 1)
    public void enable() {
        GameController gameController = this.gameController();

        SkyWarsGame coresGame = gameController.createGame(SkyWarsGame.class);
        GameInstance instance = gameController.startGame(coresGame);
    }

    @Task(event = LifeCycle.STOPPED, order = 1)
    public void disable() {

    }

}