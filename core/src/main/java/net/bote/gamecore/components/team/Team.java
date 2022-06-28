package net.bote.gamecore.components.team;

import org.jetbrains.annotations.NotNull;

public final class Team {

    private final String name;
    private final TeamColor color;

    private Team(String name, TeamColor color) {
        this.name = name;
        this.color = color;
    }

    public static @NotNull Team of(@NotNull String name, @NotNull TeamColor teamColor) {
        return new Team(name, teamColor);
    }

    public String name() {
        return this.name;
    }

    public TeamColor color() {
        return this.color;
    }
}
