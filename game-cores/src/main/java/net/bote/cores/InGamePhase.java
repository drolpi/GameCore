package net.bote.cores;

import net.bote.gamecore.api.condition.VictoryCondition;
import net.bote.gamecore.api.condition.def.TestVictoryCondition;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import net.bote.gamecore.api.phase.PhaseInfo;
import org.bukkit.GameMode;

@PhaseInfo(name = "InGamePhase", version = "2.0.0-SNAPSHOT", authors = "dasdrolpi")
final class InGamePhase extends AbstractPhase {

    public InGamePhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);

        GameModeFeature gameModeFeature = this.addFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);

        VictoryCondition victoryCondition = this.addVictoryCondition(TestVictoryCondition.class);
    }
}
