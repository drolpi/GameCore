package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.condition.AbstractVictoryCondition;
import de.drolpi.gamecore.api.condition.VictoryCondition;
import de.drolpi.gamecore.api.condition.WinnerGameData;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.components.team.TeamInstance;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class WinDetectionFeature extends AbstractFeature {

    private final Injector injector;
    private final GamePlugin plugin;
    private final Game game;

    @Expose
    private final List<AbstractVictoryCondition> victoryConditions = new ArrayList<>();

    private TeamInstance winner;

    @Inject
    public WinDetectionFeature(Injector injector, GamePlugin plugin, Game game, Phase phase) {
        this.injector = injector;
        this.plugin = plugin;
        this.game = game;
    }

    @Override
    public void enable() {
        final PluginManager pluginManager = this.plugin.getServer().getPluginManager();
        for (AbstractVictoryCondition victoryCondition : this.victoryConditions) {
            pluginManager.registerEvents(victoryCondition, this.plugin);
        }
    }

    @Override
    public void disable() {
        for (AbstractVictoryCondition victoryCondition : this.victoryConditions) {
            HandlerList.unregisterAll(victoryCondition);
        }

        WinnerGameData gameData = this.game.gameData(WinnerGameData.class).orElse(new WinnerGameData());
        gameData.winner = this.winner;
        this.game.storeGameData(gameData);
    }

    public void checkWin(AbstractVictoryCondition condition) {
        if(!condition.completed()) {
            return;
        }

        this.winner = condition.winner();
        this.game.endGame();
    }

    public <T extends AbstractVictoryCondition> T createVictoryCondition(Class<? extends T> type) {
        final T condition = this.injector.getInstance(type);
        this.victoryConditions.add(condition);
        return condition;
    }

    public List<VictoryCondition> victoryConditions() {
        return new ArrayList<>(this.victoryConditions);
    }

    public List<AbstractVictoryCondition> abstractVictoryConditions() {
        return this.victoryConditions;
    }

    public TeamInstance winner() {
        return this.winner;
    }

    public Injector injector() {
        return this.injector;
    }
}
