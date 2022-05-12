package net.bote.gamecore.test;

import net.bote.gamecore.api.AbstractGamePlugin;
import net.bote.gamecore.api.PluginInfo;
import net.bote.gamecore.api.game.GameProvider;
import net.bote.gamecore.api.task.LifeCycle;
import net.bote.gamecore.api.task.Task;

@PluginInfo(name = "Cores", description = "The Cores plugin", version = "2.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
public final class CoresPlugin extends AbstractGamePlugin {

    @Task(event = LifeCycle.STARTED, order = 1)
    public void enable() {
        GameProvider gameProvider = gameProvider();

        gameProvider.create(CoresGame.class);
    }

    @Task(event = LifeCycle.STOPPED, order = 1)
    public void disable() {

    }
}
