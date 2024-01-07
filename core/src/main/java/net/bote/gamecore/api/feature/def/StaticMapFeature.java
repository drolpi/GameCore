package net.bote.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.components.world.WorldHandler;
import net.bote.gamecore.components.world.map.MapGameData;
import net.bote.gamecore.components.world.map.MapInfo;

import java.util.Optional;

public class StaticMapFeature extends AbstractFeature {

    private final Game game;
    private final WorldHandler worldHandler;

    @Expose
    private String name;

    @Inject
    public StaticMapFeature(Game game, WorldHandler worldHandler) {
        this.game = game;
        this.worldHandler = worldHandler;
    }

    @Override
    public void enable() {
        Optional<MapInfo> info = this.worldHandler.getMapInfo(this.name);
        if (info.isPresent()) {
            MapGameData gameData = this.game.gameData(MapGameData.class).orElse(new MapGameData());
            gameData.mapInfo = info.get();
            this.game.storeGameData(gameData);
        }
    }

    @Override
    public void disable() {

    }

    public void setName(String name) {
        this.name = name;
    }
}
