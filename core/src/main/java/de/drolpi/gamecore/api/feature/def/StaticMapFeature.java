package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.components.world.map.MapInfo;
import de.drolpi.gamecore.components.world.map.MapGameData;

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
