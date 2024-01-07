package de.drolpi.gamecore.api.phase.def;

import de.drolpi.gamecore.api.feature.def.NoHungerLossFeature;
import de.drolpi.gamecore.api.counter.HandlerType;
import de.drolpi.gamecore.api.feature.def.ClearInventoryFeature;
import de.drolpi.gamecore.api.feature.def.CounterFeature;
import de.drolpi.gamecore.api.feature.def.GameModeFeature;
import de.drolpi.gamecore.api.feature.def.LevelCounterFeature;
import de.drolpi.gamecore.api.feature.def.NoBlockBreakFeature;
import de.drolpi.gamecore.api.feature.def.NoBlockPlaceFeature;
import de.drolpi.gamecore.api.feature.def.NoDamageFeature;
import de.drolpi.gamecore.api.feature.def.NoMoveFeature;
import de.drolpi.gamecore.api.feature.def.TeamFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import org.bukkit.GameMode;

public class StartingPhase extends AbstractPhase {

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);

        GameModeFeature lGameModeFeature = this.createFeature(GameModeFeature.class);
        lGameModeFeature.setGameMode(GameMode.SURVIVAL);

        this.createFeature(NoBlockBreakFeature.class);
        this.createFeature(NoBlockPlaceFeature.class);
        this.createFeature(NoDamageFeature.class);
        this.createFeature(NoHungerLossFeature.class);
        this.createFeature(NoMoveFeature.class);
        this.createFeature(ClearInventoryFeature.class);

        TeamFeature teamFeature = this.createFeature(TeamFeature.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(5);
        counterFeature.stopCount(0);
        counterFeature.registerHandler(HandlerType.TICk, counter -> {

        });
        this.createFeature(LevelCounterFeature.class);
    }
}
