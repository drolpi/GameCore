package net.bote.gamecore.test;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.game.AbstractGame;
import net.bote.gamecore.api.game.GameInfo;
import net.bote.gamecore.api.phase.def.LobbyPhase;
import net.bote.gamecore.api.team.Team;
import net.bote.gamecore.api.team.TeamColor;
import org.jetbrains.annotations.NotNull;

@GameInfo(name = "CoresGame", description = "The Cores Game", version = "2.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
final class CoresGame extends AbstractGame {

    @Expose
    private int value;

    public CoresGame(@NotNull GamePlugin gamePlugin) {
        super(gamePlugin);
    }

    @Override
    public void create() {
        this.value = 100;

        this.setTeamSize(4);
        this.addTeam(Team.of("Red", TeamColor.RED));
        this.addTeam(Team.of("Blue", TeamColor.BLUE));

        LobbyPhase lobbyPhase = this.addPhase(LobbyPhase.class);

        InGamePhase inGamePhase = this.addPhase(InGamePhase.class);
    }
}
