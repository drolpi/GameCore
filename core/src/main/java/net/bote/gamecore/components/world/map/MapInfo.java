package net.bote.gamecore.components.world.map;

import com.google.gson.annotations.Expose;

public class MapInfo {

    @Expose
    private String name;
    @Expose
    private String worldName;
    @Expose
    private String gameType;

    public MapInfo(String name, String worldName, String gameType) {
        this.name = name;
        this.worldName = worldName;
        this.gameType = gameType;
    }

    public String name() {
        return this.name;
    }

    public String worldName() {
        return this.worldName;
    }

    public String gameType() {
        return this.gameType;
    }
}
