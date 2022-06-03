package net.bote.gamecore;

import net.bote.gamecore.api.eventbus.EventBus;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.game.GameInstance;
import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

final class GameInstanceImpl implements GameInstance {

    private final Game game;
    private final EventBus eventBus;
    private Phase activePhase;

    public GameInstanceImpl(Game game) {
        this.game = game;
        this.eventBus = EventBus.create();
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
}
