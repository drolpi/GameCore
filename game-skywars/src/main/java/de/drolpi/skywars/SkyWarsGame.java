package de.drolpi.skywars;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.condition.def.LastManStandingVictoryCondition;
import de.drolpi.gamecore.api.feature.def.AutoRespawnFeature;
import de.drolpi.gamecore.api.feature.def.BossBarCounterFeature;
import de.drolpi.gamecore.api.feature.def.BossBarFeature;
import de.drolpi.gamecore.api.feature.def.MapFeature;
import de.drolpi.gamecore.api.feature.def.SpawnFeature;
import de.drolpi.gamecore.api.feature.def.SpectatorFeature;
import de.drolpi.gamecore.api.feature.def.SpectatorSpawnFeature;
import de.drolpi.gamecore.api.feature.def.StartingFeature;
import de.drolpi.gamecore.api.feature.def.StaticMapFeature;
import de.drolpi.gamecore.api.feature.def.TeamSelectFeature;
import de.drolpi.gamecore.api.feature.def.WinDetectionFeature;
import de.drolpi.gamecore.api.game.AbstractGame;
import de.drolpi.gamecore.api.game.GameInfo;
import de.drolpi.gamecore.api.phase.def.LobbyPhase;
import de.drolpi.gamecore.api.phase.def.PostGamePhase;
import de.drolpi.gamecore.api.phase.def.ProtectionPhase;
import de.drolpi.gamecore.api.phase.def.StartingPhase;
import de.drolpi.gamecore.components.team.config.Team;
import de.drolpi.gamecore.components.team.config.TeamColor;
import net.kyori.adventure.bossbar.BossBar;

@GameInfo(name = "SkyWarsGame", description = "The SkyWars game", version = "1.0.0-SNAPSHOT", authors = {"dasdrolpi"})
public final class SkyWarsGame extends AbstractGame {

    @Inject
    public SkyWarsGame() {

    }

    @Override
    public void create() {
        LobbyPhase lobbyPhase = this.createPhase(LobbyPhase.class);

        TeamSelectFeature teamFeature = lobbyPhase.createFeature(TeamSelectFeature.class);
        teamFeature.setTeamSize(1);
        teamFeature.addTeam(Team.of("T1", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T2", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T3", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T4", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T5", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T6", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T7", TeamColor.WHITE));
        teamFeature.addTeam(Team.of("T8", TeamColor.WHITE));

        StartingPhase startingPhase = this.createPhase(StartingPhase.class);

        WinDetectionFeature startingWinDetectionFeature = startingPhase.createFeature(WinDetectionFeature.class);
        startingWinDetectionFeature.createVictoryCondition(LastManStandingVictoryCondition.class);

        StaticMapFeature startingStaticMapFeature = startingPhase.createFeature(StaticMapFeature.class);
        startingStaticMapFeature.setName("Future");
        startingPhase.createFeature(MapFeature.class);
        startingPhase.createFeature(SpawnFeature.class);
        startingPhase.createFeature(AutoRespawnFeature.class);
        startingPhase.createFeature(StartingFeature.class);
        startingPhase.createFeature(SpectatorFeature.class);
        startingPhase.createFeature(SpectatorSpawnFeature.class);

        ProtectionPhase protectionPhase = this.createPhase(ProtectionPhase.class);
        StaticMapFeature protectionStaticMapFeature = protectionPhase.createFeature(StaticMapFeature.class);
        protectionStaticMapFeature.setName("Future");
        protectionPhase.createFeature(MapFeature.class);
        protectionPhase.createFeature(AutoRespawnFeature.class);
        protectionPhase.createFeature(SpectatorFeature.class);
        protectionPhase.createFeature(SpectatorSpawnFeature.class);

        protectionPhase.createFeature(BossBarFeature.class);
        protectionPhase.createFeature(BossBarCounterFeature.class);

        WinDetectionFeature protectionWinDetectionFeature = protectionPhase.createFeature(WinDetectionFeature.class);
        protectionWinDetectionFeature.createVictoryCondition(LastManStandingVictoryCondition.class);

        this.createPhase(InGamePhase.class);
        this.createPhase(PostGamePhase.class);
    }
}
