package de.drolpi.gamecore.api.phase.def;

import de.drolpi.gamecore.api.feature.def.AnimatedSpawnFeature;
import de.drolpi.gamecore.api.feature.def.AutoRespawnFeature;
import de.drolpi.gamecore.api.feature.def.ClearInventoryFeature;
import de.drolpi.gamecore.api.feature.def.CounterFeature;
import de.drolpi.gamecore.api.feature.def.GameModeFeature;
import de.drolpi.gamecore.api.feature.def.LevelCounterFeature;
import de.drolpi.gamecore.api.feature.def.MessageCounterFeature;
import de.drolpi.gamecore.api.feature.def.NoBlockPlaceFeature;
import de.drolpi.gamecore.api.feature.def.NoHungerLossFeature;
import de.drolpi.gamecore.api.feature.def.NoItemDropFeature;
import de.drolpi.gamecore.api.feature.def.NoWeatherChangeFeature;
import de.drolpi.gamecore.api.feature.def.PostGameFeature;
import de.drolpi.gamecore.api.feature.def.RemovePotionEffectsFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import de.drolpi.gamecore.api.feature.def.StaticMapFeature;
import de.drolpi.gamecore.api.feature.def.MapFeature;
import de.drolpi.gamecore.api.feature.def.NoBlockBreakFeature;
import de.drolpi.gamecore.api.feature.def.NoDamageFeature;
import de.drolpi.gamecore.api.feature.def.NoItemPickupFeature;
import de.drolpi.gamecore.api.feature.def.NoTimeChangeFeature;
import de.drolpi.gamecore.api.feature.def.SpawnFeature;
import de.drolpi.gamecore.api.phase.PhaseInfo;
import org.bukkit.GameMode;

@PhaseInfo(name = "PostGame", key = "ending")
public class PostGamePhase extends AbstractPhase {

    @Override
    public void create() {
        this.setAllowJoin(true);
        this.setAllowSpectate(false);

        GameModeFeature gameModeFeature = this.createFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);

        this.createFeature(NoBlockBreakFeature.class);
        this.createFeature(NoBlockPlaceFeature.class);
        this.createFeature(NoItemDropFeature.class);
        this.createFeature(NoItemPickupFeature.class);
        this.createFeature(NoDamageFeature.class);
        this.createFeature(NoHungerLossFeature.class);
        this.createFeature(ClearInventoryFeature.class);
        this.createFeature(RemovePotionEffectsFeature.class);

        StaticMapFeature staticMapFeature = this.createFeature(StaticMapFeature.class);
        staticMapFeature.setName("Lobby");

        MapFeature mapFeature = this.createFeature(MapFeature.class);
        mapFeature.setShouldUnload(true);
        this.createFeature(SpawnFeature.class);
        this.createFeature(AnimatedSpawnFeature.class);

        this.createFeature(NoWeatherChangeFeature.class);
        this.createFeature(NoTimeChangeFeature.class);
        this.createFeature(AutoRespawnFeature.class);

        PostGameFeature feature = this.createFeature(PostGameFeature.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(15);
        counterFeature.stopCount(0);

        this.createFeature(LevelCounterFeature.class);
        this.createFeature(MessageCounterFeature.class);
    }
}
