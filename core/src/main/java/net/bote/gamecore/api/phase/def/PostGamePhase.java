package net.bote.gamecore.api.phase.def;

import net.bote.gamecore.api.feature.def.ClearInventoryFeature;
import net.bote.gamecore.api.feature.def.CounterFeature;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.feature.def.LevelCounterFeature;
import net.bote.gamecore.api.feature.def.StaticMapFeature;
import net.bote.gamecore.api.feature.def.MapFeature;
import net.bote.gamecore.api.feature.def.NoBlockBreakFeature;
import net.bote.gamecore.api.feature.def.NoBlockPlaceFeature;
import net.bote.gamecore.api.feature.def.NoDamageFeature;
import net.bote.gamecore.api.feature.def.NoHungerLossFeature;
import net.bote.gamecore.api.feature.def.NoItemDropFeature;
import net.bote.gamecore.api.feature.def.NoItemPickupFeature;
import net.bote.gamecore.api.feature.def.NoTimeChangeFeature;
import net.bote.gamecore.api.feature.def.NoWeatherChangeFeature;
import net.bote.gamecore.api.feature.def.PostGameFeature;
import net.bote.gamecore.api.feature.def.RemovePotionEffectsFeature;
import net.bote.gamecore.api.feature.def.SpawnFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import org.bukkit.GameMode;

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

        this.createFeature(NoWeatherChangeFeature.class);
        this.createFeature(NoTimeChangeFeature.class);

        PostGameFeature feature = this.createFeature(PostGameFeature.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(15);
        counterFeature.stopCount(0);

        this.createFeature(LevelCounterFeature.class);
    }
}
