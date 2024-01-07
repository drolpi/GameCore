package net.bote.skywars;

import com.google.inject.Inject;
import net.bote.gamecore.api.condition.def.LastManStandingVictoryCondition;
import net.bote.gamecore.api.feature.def.AutoRespawnFeature;
import net.bote.gamecore.api.feature.def.BossBarCounterFeature;
import net.bote.gamecore.api.feature.def.BossBarFeature;
import net.bote.gamecore.api.feature.def.MapFeature;
import net.bote.gamecore.api.feature.def.SpawnFeature;
import net.bote.gamecore.api.feature.def.StaticMapFeature;
import net.bote.gamecore.api.feature.def.TeamFeature;
import net.bote.gamecore.api.feature.def.TeamSelectFeature;
import net.bote.gamecore.api.feature.def.WinDetectionFeature;
import net.bote.gamecore.api.game.AbstractGame;
import net.bote.gamecore.api.game.GameInfo;
import net.bote.gamecore.api.phase.def.LobbyPhase;
import net.bote.gamecore.api.phase.def.PostGamePhase;
import net.bote.gamecore.api.phase.def.ProtectionPhase;
import net.bote.gamecore.api.phase.def.StartingPhase;
import net.bote.gamecore.components.team.config.Team;
import net.bote.gamecore.components.team.config.TeamColor;
import org.bukkit.boss.BarColor;

@GameInfo(name = "SkyWarsGame", description = "The SkyWars game", version = "3.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
public final class SkyWarsGame extends AbstractGame {

    @Inject
    public SkyWarsGame() {

    }

    @Override
    public void create() {
        LobbyPhase lobbyPhase = this.createPhase(LobbyPhase.class);
        lobbyPhase.setMinPlayers(2);

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

        ProtectionPhase protectionPhase = this.createPhase(ProtectionPhase.class);
        StaticMapFeature protectionStaticMapFeature = protectionPhase.createFeature(StaticMapFeature.class);
        protectionStaticMapFeature.setName("Future");
        protectionPhase.createFeature(MapFeature.class);
        protectionPhase.createFeature(AutoRespawnFeature.class);

        BossBarFeature bossBarFeature = protectionPhase.createFeature(BossBarFeature.class);
        bossBarFeature.setColor(BarColor.GREEN);

        protectionPhase.createFeature(BossBarCounterFeature.class);

        WinDetectionFeature protectionWinDetectionFeature = protectionPhase.createFeature(WinDetectionFeature.class);
        protectionWinDetectionFeature.createVictoryCondition(LastManStandingVictoryCondition.class);

        this.createPhase(InGamePhase.class);
        this.createPhase(PostGamePhase.class);
    }
}
