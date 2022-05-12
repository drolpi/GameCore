package net.bote.gamecore.api.game;

public interface GameProvider {

    void create(Class<? extends Game> type);

}
