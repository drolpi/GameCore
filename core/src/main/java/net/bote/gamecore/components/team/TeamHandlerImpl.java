package net.bote.gamecore.components.team;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class TeamHandlerImpl implements TeamHandler {

    private final Map<String, TeamInstance> teams;

    public TeamHandlerImpl() {
        this.teams = new HashMap<>();
    }

    @Override
    public @NotNull Optional<TeamInstance> teamByName(@NotNull String name) {
        return Optional.ofNullable(this.teams.get(name));
    }

    @Override
    public @NotNull Optional<TeamInstance> teamByPlayer(@NotNull Player player) {
        return this.teams.values().stream().filter(team -> team.players().isTeamMate(player)).findFirst();
    }

    @Override
    public @NotNull TeamInstance mostFullAndFreeTeam() {
        return this.teams
            .values()
            .stream()
            .filter(gameTeam -> !gameTeam.isFull())
            .max(Comparator.comparingInt(value -> value.players().count()))
            .orElseThrow(() -> new IllegalStateException("No teams present"));
    }

    @Override
    public @NotNull Set<TeamInstance> teams() {
        return Collections.unmodifiableSet(new HashSet<>(this.teams.values()));
    }

    @Override
    public @NotNull Set<TeamInstance> aliveTeams() {
        return this.teams().stream().filter(TeamInstance::isAlive).collect(Collectors.toSet());
    }

    @Override
    public int aliveTeamCount() {
        return this.aliveTeams().size();
    }

    @Override
    public int alivePlayerCount() {
        return this.aliveTeams().stream().mapToInt(value -> value.players().count()).sum();
    }

    @Override
    public void fillTeams(@NotNull Collection<? extends Player> players) {
        for (Player player : players) {
            if (!this.isInTeam(player)) {
                TeamInstance team = this.mostFullAndFreeTeam();
                team.players().add(player);
            }
        }
    }

    @Override
    public boolean isInTeam(@NotNull Player player) {
        for (TeamInstance team : this.teams()) {
            if (team.players().isTeamMate(player)) {
                return true;
            }
        }
        return false;
    }

}
