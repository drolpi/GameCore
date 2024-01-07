package net.bote.cores;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.game.AbstractGame;
import net.bote.gamecore.api.game.GameInfo;
import net.bote.gamecore.api.phase.def.LobbyPhase;
import org.bukkit.GameMode;

@GameInfo(name = "CoresGame", description = "The Cores game", version = "2.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
final class CoresGame extends AbstractGame {

    @Expose
    private int value;

    @Inject
    public CoresGame() {

    }

    @Override
    public void create() {
        this.value = 100;

        LobbyPhase lobbyPhase = this.createPhase(LobbyPhase.class);
        lobbyPhase.setMinPlayers(3);

        GameModeFeature gameModeFeature = lobbyPhase.createFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SPECTATOR);

        InGamePhase inGamePhase = this.createPhase(InGamePhase.class);
    }
}
