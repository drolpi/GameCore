package de.drolpi.gamecore.api.game;

import java.io.File;

final class GameTypeImpl implements GameType {

    private final String name;
    private final Class<? extends Game> type;
    private final File dataFolder;
    private final File gameFile;

    public GameTypeImpl(String name, Class<? extends Game> type, File dataFolder) {
        this.name = name;
        this.type = type;
        this.dataFolder = new File(dataFolder, name);
        this.gameFile = new File(this.dataFolder, name + ".json");
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Class<? extends Game> type() {
        return this.type;
    }

    @Override
    public File dataFolder() {
        return this.dataFolder;
    }

    @Override
    public File gameFile() {
        return this.gameFile;
    }
}
