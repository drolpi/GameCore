package net.bote.cores;

import com.google.inject.Inject;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import net.bote.gamecore.api.phase.PhaseInfo;
import org.bukkit.GameMode;

@PhaseInfo(name = "InGamePhase", version = "2.0.0-SNAPSHOT", authors = "dasdrolpi")
final class InGamePhase extends AbstractPhase {

    @Inject
    public InGamePhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);

        GameModeFeature gameModeFeature = this.createFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);
    }
}
