package de.drolpi.gamecore.components.world.map.storage;

import de.drolpi.gamecore.components.world.map.MapInfo;

import java.util.HashSet;
import java.util.Set;

public final class JsonMapInfoStorage implements MapInfoStorage {

    private final Set<MapInfo> mapInfos = new HashSet<>();

    @Override
    public void load() {
        this.mapInfos.add(new MapInfo("Lobby", "WarteLobby", "SkyWarsGame"));
        this.mapInfos.add(new MapInfo("Future", "SWFuture", "SkyWarsGame"));
    }

    @Override
    public void save() {

    }

    @Override
    public Set<MapInfo> mapInfos() {
        return this.mapInfos;
    }
}
