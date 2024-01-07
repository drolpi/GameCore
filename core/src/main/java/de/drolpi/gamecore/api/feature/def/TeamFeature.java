package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.team.TeamInstance;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.components.team.TeamGameData;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamFeature extends AbstractFeature {

    protected final Game game;

    protected Map<String, TeamInstance> teamInstances;

    @Inject
    public TeamFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {
        TeamGameData gameData = this.game.gameData(TeamGameData.class).orElse(new TeamGameData());
        this.teamInstances = gameData.teams;
        if (this.teamInstances == null || this.teamInstances.size() == 0) {
            /*
            log.severe("You need to run team select before running team feature!");
            getPhase().getGame().abortGame();
             */
        }
    }

    @Override
    public void disable() {
        this.fillTeams(this.game.players());

        TeamGameData teamGameData = this.game.gameData(TeamGameData.class).orElse(new TeamGameData());
        teamGameData.teams = this.teamInstances;
        this.game.storeGameData(teamGameData);
    }

    private void fillTeams(@NotNull Collection<GamePlayer> players) {
        for (GamePlayer player : players) {
            if (!this.isInTeam(player)) {
                TeamInstance team = this.mostFullAndFreeTeam();
                team.players().add(player);
            }
        }
    }

    private @NotNull TeamInstance mostFullAndFreeTeam() {
        return this.teamInstances
            .values()
            .stream()
            .filter(gameTeam -> !gameTeam.isFull())
            .max(Comparator.comparingInt(value -> value.players().count()))
            .orElseThrow(() -> new IllegalStateException("No teams present"));
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        this.removePlayer(event.gamePlayer());
    }

    public void removePlayer(GamePlayer player) {
        Optional<TeamInstance> team = this.teamByPlayer(player);
        team.ifPresent(teamInstance -> teamInstance.players().remove(player));
    }

    public @NotNull Optional<TeamInstance> teamByName(@NotNull String name) {
        return Optional.ofNullable(this.teamInstances.get(name));
    }

    public @NotNull Optional<TeamInstance> teamByPlayer(@NotNull GamePlayer player) {
        return this.teamInstances.values().stream().filter(team -> team.players().isTeamMate(player)).findFirst();
    }

    public boolean isInTeam(@NotNull GamePlayer player) {
        for (TeamInstance team : this.teams()) {
            if (team.players().isTeamMate(player)) {
                return true;
            }
        }
        return false;
    }

    public @NotNull Set<TeamInstance> teams() {
        return Set.copyOf(this.teamInstances.values());
    }

    public @NotNull Set<TeamInstance> aliveTeams() {
        return this.teams().stream().filter(TeamInstance::isAlive).collect(Collectors.toSet());
    }

    public int aliveTeamCount() {
        return this.aliveTeams().size();
    }

    public int alivePlayerCount() {
        return this.aliveTeams().stream().mapToInt(value -> value.players().count()).sum();
    }
}
