package de.drolpi.gamecore.api.game;

public interface GameType {

    String name();

    Class<? extends Game> type();

}
