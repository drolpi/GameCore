package de.drolpi.gamecore.api.game;

import de.drolpi.gamecore.api.player.GamePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface GameController {

    GameType createGameType(@NotNull Class<? extends Game> type);

    <T extends Game> @NotNull T startGame(@NotNull GameType gameType);

    Set<Game> games(GamePlayer gamePlayer, boolean spectate);

    Set<Game> games();
}
