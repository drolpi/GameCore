package net.bote.gamecore.components.team;

import net.bote.gamecore.components.team.config.Team;
import net.bote.gamecore.components.team.config.TeamColor;
import org.jetbrains.annotations.NotNull;

public final class TeamInstanceImpl implements TeamInstance {

    private final Team team;
    private final int maxPlayers;
    private final TeamPlayers teamPlayers;

    public TeamInstanceImpl(Team team, int maxPlayers) {
        this.team = team;
        this.maxPlayers = maxPlayers;
        this.teamPlayers = new TeamPlayersImpl(this);
    }

    @Override
    public @NotNull String name() {
        return this.team.name();
    }

    @Override
    public @NotNull TeamColor color() {
        return this.team.color();
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
