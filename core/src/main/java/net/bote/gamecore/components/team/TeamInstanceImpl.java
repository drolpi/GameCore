package net.bote.gamecore.components.team;

import org.jetbrains.annotations.NotNull;

final class TeamInstanceImpl implements TeamInstance {

    private final String name;
    private final TeamColor color;
    private final int maxPlayers;
    private final TeamPlayers teamPlayers;

    public TeamInstanceImpl(String name, TeamColor color, int maxPlayers) {
        this.name = name;
        this.color = color;
        this.maxPlayers = maxPlayers;
        this.teamPlayers = new TeamPlayersImpl(this);
    }

    @Override
    public @NotNull String name() {
        return this.name;
    }

    @Override
    public @NotNull TeamColor color() {
        return this.color;
    }

    @Override
    public @NotNull TeamPlayers players() {
        return this.teamPlayers;
    }

    @Override
    public boolean isAlive() {
        return this.teamPlayers.count() > 0;
    }

    @Override
    public boolean isFull() {
        return this.teamPlayers.count() == maxPlayers;
    }
}
