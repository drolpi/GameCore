package net.bote.gamecore.components.team;

import net.bote.gamecore.components.team.config.TeamColor;
import org.jetbrains.annotations.NotNull;

public interface TeamInstance {

    @NotNull String name();

    @NotNull TeamColor color();

    @NotNull TeamPlayers players();

    boolean isAlive();

    boolean isFull();

}
