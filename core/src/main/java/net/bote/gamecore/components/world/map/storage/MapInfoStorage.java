package net.bote.gamecore.components.world.map.storage;

import net.bote.gamecore.components.world.map.MapInfo;

import java.util.Set;

public interface MapInfoStorage {

    void load();

    void save();

    Set<MapInfo> mapInfos();
}
