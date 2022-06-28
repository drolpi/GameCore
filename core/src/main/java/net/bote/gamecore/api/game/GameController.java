package net.bote.gamecore.api.game;

import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

public interface GameController {

    <T extends Game> @NotNull T createGame(@NotNull Class<? extends T> type);

    @NotNull GameInstance startGame(@NotNull Game game);

    void startPhase(@NotNull GameInstance gameInstance, @NotNull Phase phase);
}
