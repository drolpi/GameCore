package de.drolpi.skywars;

import de.drolpi.gamecore.api.condition.def.LastManStandingVictoryCondition;
import de.drolpi.gamecore.api.feature.def.AutoRespawnFeature;
import de.drolpi.gamecore.api.feature.def.CounterFeature;
import de.drolpi.gamecore.api.feature.def.DeathMessageFeature;
import de.drolpi.gamecore.api.feature.def.GameModeFeature;
import de.drolpi.gamecore.api.feature.def.MapFeature;
import de.drolpi.gamecore.api.feature.def.MessageCounterFeature;
import de.drolpi.gamecore.api.feature.def.StaticMapFeature;
import de.drolpi.gamecore.api.feature.def.TeamFeature;
import de.drolpi.gamecore.api.feature.def.WinDetectionFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import de.drolpi.gamecore.api.phase.PhaseInfo;
import org.bukkit.GameMode;

import java.util.concurrent.TimeUnit;

@PhaseInfo(name = "InGamePhase", key = "ingame", version = "3.0.0-SNAPSHOT", authors = "dasdrolpi")
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
        this.createFeature(DeathMessageFeature.class);

        WinDetectionFeature winDetectionFeature = this.createFeature(WinDetectionFeature.class);
        winDetectionFeature.createVictoryCondition(LastManStandingVictoryCondition.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(60);
        counterFeature.stopCount(0);
        counterFeature.timeUnit(TimeUnit.MINUTES);

        this.createFeature(MessageCounterFeature.class);
    }
}
