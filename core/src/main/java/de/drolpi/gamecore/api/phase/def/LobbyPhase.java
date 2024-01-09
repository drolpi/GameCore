package de.drolpi.gamecore.api.phase.def;

import de.drolpi.gamecore.api.feature.def.AnimatedSpawnFeature;
import de.drolpi.gamecore.api.feature.def.MessageCounterFeature;
import de.drolpi.gamecore.api.feature.def.NoHungerLossFeature;
import de.drolpi.gamecore.api.feature.def.NoItemDropFeature;
import de.drolpi.gamecore.api.feature.def.RemovePotionEffectsFeature;
import de.drolpi.gamecore.api.feature.def.SoundCounterFeature;
import de.drolpi.gamecore.api.feature.def.TitleCounterFeature;
import de.drolpi.gamecore.api.phase.PhaseInfo;
import de.drolpi.gamecore.api.feature.def.ClearInventoryFeature;
import de.drolpi.gamecore.api.feature.def.CounterFeature;
import de.drolpi.gamecore.api.feature.def.GameModeFeature;
import de.drolpi.gamecore.api.feature.def.LevelCounterFeature;
import de.drolpi.gamecore.api.feature.def.LobbyFeature;
import de.drolpi.gamecore.api.feature.def.StaticMapFeature;
import de.drolpi.gamecore.api.feature.def.MapFeature;
import de.drolpi.gamecore.api.feature.def.NoBlockBreakFeature;
import de.drolpi.gamecore.api.feature.def.NoBlockPlaceFeature;
import de.drolpi.gamecore.api.feature.def.NoDamageFeature;
import de.drolpi.gamecore.api.feature.def.NoItemPickupFeature;
import de.drolpi.gamecore.api.feature.def.NoTimeChangeFeature;
import de.drolpi.gamecore.api.feature.def.NoWeatherChangeFeature;
import de.drolpi.gamecore.api.feature.def.SpawnFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import org.bukkit.GameMode;

@PhaseInfo(name = "LobbyPhase", key = "lobby", version = "1.0", authors = "dasdrolpi")
public class LobbyPhase extends AbstractPhase {

    @Override
    public void create() {
        this.setAllowJoin(true);
        this.setAllowSpectate(false);

        GameModeFeature lGameModeFeature = this.createFeature(GameModeFeature.class);
        lGameModeFeature.setGameMode(GameMode.SURVIVAL);

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

        this.createFeature(MapFeature.class);
        this.createFeature(SpawnFeature.class);
        this.createFeature(AnimatedSpawnFeature.class);
        this.createFeature(NoWeatherChangeFeature.class);
        this.createFeature(NoTimeChangeFeature.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(60);
        counterFeature.stopCount(0);
        counterFeature.autoStart(false);

        this.createFeature(LevelCounterFeature.class);
        this.createFeature(LobbyFeature.class);
        TitleCounterFeature titleCounterFeature = this.createFeature(TitleCounterFeature.class);
        titleCounterFeature.setOnFinish(false);
        MessageCounterFeature messageCounterFeature = this.createFeature(MessageCounterFeature.class);
        messageCounterFeature.setOnCancel(true);
        this.createFeature(SoundCounterFeature.class);
    }
}
