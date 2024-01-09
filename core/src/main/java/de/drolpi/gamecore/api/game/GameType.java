package de.drolpi.gamecore.api.game;

import java.io.File;

public interface GameType {

    String name();

    Class<? extends Game> type();

    File dataFolder();

    File gameFile();
}
