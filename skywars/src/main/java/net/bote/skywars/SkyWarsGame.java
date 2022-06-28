package net.bote.skywars;

import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.game.AbstractGame;
import net.bote.gamecore.api.game.GameInfo;
import net.bote.gamecore.api.phase.def.LobbyPhase;
import net.bote.gamecore.components.team.Team;
import net.bote.gamecore.components.team.TeamColor;
import org.jetbrains.annotations.NotNull;

@GameInfo(name = "SkyWarsGame", description = "The SkyWars game", version = "3.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
public final class SkyWarsGame extends AbstractGame {

    public SkyWarsGame(@NotNull GamePlugin gamePlugin) {
        super(gamePlugin);
    }

    @Override
    public void create() {
        this.setTeamSize(1);
        this.addTeam(Team.of("T1", TeamColor.WHITE));
        this.addTeam(Team.of("T2", TeamColor.WHITE));
        this.addTeam(Team.of("T3", TeamColor.WHITE));
        this.addTeam(Team.of("T4", TeamColor.WHITE));
        this.addTeam(Team.of("T5", TeamColor.WHITE));
        this.addTeam(Team.of("T6", TeamColor.WHITE));
        this.addTeam(Team.of("T7", TeamColor.WHITE));
        this.addTeam(Team.of("T8", TeamColor.WHITE));

        LobbyPhase lobbyPhase = this.addPhase(LobbyPhase.class);
        lobbyPhase.setMinPlayers(2);

        ProtectionPhase protectionPhase = this.addPhase(ProtectionPhase.class);

        InGamePhase inGamePhase = this.addPhase(InGamePhase.class);
    }
}
