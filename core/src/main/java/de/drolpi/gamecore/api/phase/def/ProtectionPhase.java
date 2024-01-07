package de.drolpi.gamecore.api.phase.def;

import de.drolpi.gamecore.api.feature.def.ClearInventoryFeature;
import de.drolpi.gamecore.api.feature.def.CounterFeature;
import de.drolpi.gamecore.api.feature.def.GameModeFeature;
import de.drolpi.gamecore.api.feature.def.NoDamageFeature;
import de.drolpi.gamecore.api.feature.def.TeamFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import org.bukkit.GameMode;

public class ProtectionPhase extends AbstractPhase {

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);

        GameModeFeature gameModeFeature = this.createFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);

        this.createFeature(ClearInventoryFeature.class);
        this.createFeature(NoDamageFeature.class);

        TeamFeature teamFeature = this.createFeature(TeamFeature.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(30);
        counterFeature.stopCount(0);
    }
}
