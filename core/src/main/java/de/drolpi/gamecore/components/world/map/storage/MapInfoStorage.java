package de.drolpi.gamecore.components.world.map.storage;

import de.drolpi.gamecore.components.world.map.MapInfo;

import java.util.Set;

public interface MapInfoStorage {

    void load();

    void save();

    Set<MapInfo> mapInfos();
}
