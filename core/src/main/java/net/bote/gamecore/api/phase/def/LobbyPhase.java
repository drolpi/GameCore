package net.bote.gamecore.api.phase.def;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.feature.def.ClearInventoryFeature;
import net.bote.gamecore.api.feature.def.CounterFeature;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.feature.def.LevelCounterFeature;
import net.bote.gamecore.api.feature.def.LobbyFeature;
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
import net.bote.gamecore.api.feature.def.RemovePotionEffectsFeature;
import net.bote.gamecore.api.feature.def.SpawnFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import net.bote.gamecore.api.phase.PhaseInfo;
import org.bukkit.GameMode;

@PhaseInfo(name = "LobbyPhase", version = "1.0", authors = "dasdrolpi")
public class LobbyPhase extends AbstractPhase {

    @Expose
    private int minPlayers;

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
        this.createFeature(NoWeatherChangeFeature.class);
        this.createFeature(NoTimeChangeFeature.class);

        CounterFeature counterFeature = this.createFeature(CounterFeature.class);
        counterFeature.startCount(60);
        counterFeature.stopCount(0);
        counterFeature.autoStart(false);

        this.createFeature(LevelCounterFeature.class);
        this.createFeature(LobbyFeature.class);
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
}
