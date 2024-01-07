package de.drolpi.skywars;

import de.drolpi.gamecore.api.condition.def.LastManStandingVictoryCondition;
import de.drolpi.gamecore.api.feature.def.AutoRespawnFeature;
import de.drolpi.gamecore.api.feature.def.GameModeFeature;
import de.drolpi.gamecore.api.feature.def.MapFeature;
import de.drolpi.gamecore.api.feature.def.StaticMapFeature;
import de.drolpi.gamecore.api.feature.def.TeamFeature;
import de.drolpi.gamecore.api.feature.def.WinDetectionFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import de.drolpi.gamecore.api.phase.PhaseInfo;
import org.bukkit.GameMode;

@PhaseInfo(name = "InGamePhase", version = "3.0.0-SNAPSHOT", authors = "dasdrolpi")
public final class InGamePhase extends AbstractPhase {

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);

        GameModeFeature gameModeFeature = this.createFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);

        StaticMapFeature staticMapFeature = this.createFeature(StaticMapFeature.class);
        staticMapFeature.setName("Future");
        MapFeature mapFeature = this.createFeature(MapFeature.class);
        mapFeature.setShouldUnload(true);
        this.createFeature(AutoRespawnFeature.class);

        TeamFeature teamFeature = this.createFeature(TeamFeature.class);

        WinDetectionFeature winDetectionFeature = this.createFeature(WinDetectionFeature.class);
        winDetectionFeature.createVictoryCondition(LastManStandingVictoryCondition.class);
    }
}
