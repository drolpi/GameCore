package de.drolpi.gamecore.components.team;

import de.drolpi.gamecore.components.team.config.TeamColor;
import org.jetbrains.annotations.NotNull;

public interface TeamInstance {

    @NotNull String name();

    @NotNull TeamColor color();

    @NotNull TeamPlayers players();

    boolean isAlive();

    boolean isFull();

}
