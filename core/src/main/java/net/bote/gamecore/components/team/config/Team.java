package net.bote.gamecore.components.team.config;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

public class Team {

    @Expose
    private final String name;
    @Expose
    private final TeamColor color;

    protected Team(String name, TeamColor color) {
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
