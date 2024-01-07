package net.bote.gamecore.api.phase.def;

import com.google.inject.Inject;
import net.bote.gamecore.api.feature.def.AutoRespawnFeature;
import net.bote.gamecore.api.feature.def.ClearInventoryFeature;
import net.bote.gamecore.api.feature.def.CounterFeature;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.feature.def.NoDamageFeature;
import net.bote.gamecore.api.feature.def.TeamFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
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
