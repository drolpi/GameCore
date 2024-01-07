package de.drolpi.gamecore.api.game;

final class GameTypeImpl implements GameType {

    private final String name;
    private final Class<? extends Game> type;

    public GameTypeImpl(String name, Class<? extends Game> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Class<? extends Game> type() {
        return this.type;
    }
}
