package net.bote.gamecore.components.team;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface TeamHandler {

    @NotNull Optional<TeamInstance> teamByName(@NotNull String name);

    @NotNull Optional<TeamInstance> teamByPlayer(@NotNull Player player);

    @NotNull TeamInstance mostFullAndFreeTeam();

    @NotNull Set<TeamInstance> teams();

    @NotNull Set<TeamInstance> aliveTeams();

    int aliveTeamCount();

    int alivePlayerCount();

    void fillTeams(@NotNull Collection<? extends Player> players);

    boolean isInTeam(@NotNull Player player);
}
