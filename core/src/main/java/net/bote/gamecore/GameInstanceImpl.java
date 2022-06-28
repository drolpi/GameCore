package net.bote.gamecore;

import net.bote.gamecore.api.eventbus.EventBus;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.game.GameInstance;
import net.bote.gamecore.api.phase.Phase;
import net.bote.gamecore.components.team.TeamHandler;
import net.bote.gamecore.components.team.TeamHandlerImpl;
import org.jetbrains.annotations.NotNull;

final class GameInstanceImpl implements GameInstance {

    private final Game game;
    private final EventBus eventBus;
    private final TeamHandler teamHandler;
    private Phase activePhase;

    public GameInstanceImpl(Game game) {
        this.game = game;
        this.eventBus = EventBus.create();
        this.teamHandler = new TeamHandlerImpl();
    }

    @Override
    public Phase activePhase() {
        return this.activePhase;
    }

    @Override
    public void setActivePhase(@NotNull Phase phase) {
        this.activePhase = phase;
    }

    @Override
    public @NotNull EventBus eventBus() {
        return this.eventBus;
    }

    @Override
    public @NotNull TeamHandler teamHandler() {
        return teamHandler;
    }
}
