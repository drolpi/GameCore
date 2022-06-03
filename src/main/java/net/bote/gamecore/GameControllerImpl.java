package net.bote.gamecore;

import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.game.GameInstance;
import net.bote.gamecore.api.game.GameController;
import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

final class GameControllerImpl implements GameController {

    @Override
    public <T extends Game> @NotNull T createGame(@NotNull Class<? extends T> type) {
        return null;
    }

    @Override
    public @NotNull GameInstance startGame(@NotNull Game game) {
        GameInstance gameInstance = new GameInstanceImpl(game);

        this.startPhase(gameInstance, game.phases().get(0));

        return gameInstance;
    }

    @Override
    public void startPhase(@NotNull GameInstance gameInstance, @NotNull Phase phase) {
        gameInstance.setActivePhase(phase);

        
    }
}
