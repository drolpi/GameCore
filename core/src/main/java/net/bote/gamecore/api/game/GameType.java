package net.bote.gamecore.api.game;

public interface GameType {

    String name();

    Class<? extends Game> type();

}
