package net.bote.cores;

import net.bote.gamecore.AbstractGamePlugin;
import net.bote.gamecore.PluginInfo;
import net.bote.gamecore.api.game.GameController;
import net.bote.gamecore.api.game.GameType;
import net.bote.gamecore.api.task.LifeCycle;
import net.bote.gamecore.api.task.Task;

@PluginInfo(name = "Cores", description = "The Cores plugin", version = "2.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
public final class CoresPlugin extends AbstractGamePlugin {

    @Task(event = LifeCycle.STARTED, order = 1)
    public void enable() {
        GameController gameController = this.gameController();
        GameType cores = gameController.createGameType(CoresGame.class);

        CoresGame coresGame = gameController.startGame(cores);
    }

    @Task(event = LifeCycle.STOPPED, order = 1)
    public void disable() {

    }
}
