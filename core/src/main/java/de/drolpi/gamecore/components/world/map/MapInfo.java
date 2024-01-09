package de.drolpi.gamecore.components.world.map;

import com.google.gson.annotations.Expose;

public class MapInfo {

    @Expose
    private String name;
    @Expose
    private String worldName;
    @Expose
    private String[] gameTypes;

    public MapInfo(String name, String worldName, String... gameTypes) {
        this.name = name;
        this.worldName = worldName;
        this.gameTypes = gameTypes;
    }

    public String name() {
        return this.name;
    }

    public String worldName() {
        return this.worldName;
    }

    public String[] gameTypes() {
        return this.gameTypes;
    }
}
