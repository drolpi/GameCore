package de.drolpi.gamecore.components.world.map.storage;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.drolpi.gamecore.components.world.map.MapInfo;
import de.drolpi.gamecore.internal.config.ConfigLoader;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class JsonMapInfoStorage implements MapInfoStorage {

    private final File mapInfosFile;
    private final ConfigLoader configLoader;
    private Storage storage;

    @Inject
    public JsonMapInfoStorage(@Named("DataFolder") File dataFolder, ConfigLoader configLoader) {
        this.mapInfosFile = new File(dataFolder, "maps.json");
        this.configLoader = configLoader;
    }

    @Override
    public void load() {
        //TODO: Remove
        Storage defaults = new Storage();
        defaults.addDefaults();
        this.storage = this.configLoader.load(this.mapInfosFile, defaults);
    }

    @Override
    public void save() {
        this.configLoader.save(this.mapInfosFile, this.storage);
    }

    @Override
    public Set<MapInfo> mapInfos() {
        return this.storage.maps;
    }

    static class Storage {

        @Expose
        private final HashSet<MapInfo> maps = new HashSet<>();

        public void addDefaults() {
            this.maps.add(new MapInfo("Lobby", "WarteLobby"));
            this.maps.add(new MapInfo("Future", "SWFuture", "SkyWarsGame"));
        }
    }
}
