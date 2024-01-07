package net.bote.gamecore.api.game;

import net.bote.gamecore.api.phase.Phase;
import net.bote.gamecore.api.player.GamePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Game {

    boolean join(GamePlayer gamePlayer);

    boolean isPlaying(UUID uniqueId);

    List<GamePlayer> players();

    boolean spectate(GamePlayer gamePlayer);

    boolean isSpectating(UUID uniqueID);

    List<GamePlayer> spectators();

    boolean leave(GamePlayer gamePlayer);

    boolean isParticipating(UUID uniqueId);

    List<GamePlayer> allPlayers();

    void nextPhase();

    Phase activePhase();

    @NotNull List<Phase> phases();

    void endGame();

    void stopGame();

    boolean isEnded();

    void storeGameData(GameData data);

    <T extends GameData> Optional<T> gameData(Class<T> key);
}
