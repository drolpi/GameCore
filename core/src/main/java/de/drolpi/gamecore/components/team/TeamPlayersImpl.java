package de.drolpi.gamecore.components.team;

import de.drolpi.gamecore.components.team.result.AddResult;
import de.drolpi.gamecore.components.team.result.AlreadyInTeamResult;
import de.drolpi.gamecore.components.team.result.SuccessfulAddResult;
import de.drolpi.gamecore.components.team.result.TeamFullResult;
import de.drolpi.gamecore.api.player.GamePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

final class TeamPlayersImpl implements TeamPlayers {

    private final TeamInstance team;
    private final Set<GamePlayer> players;

    public TeamPlayersImpl(TeamInstance team) {
        this.team = team;
        this.players = Collections.newSetFromMap(new WeakHashMap<>());
    }

    @Override
    public @NotNull TeamInstance team() {
        return this.team;
    }

    @Override
    public @NotNull Set<GamePlayer> collect() {
        return Collections.unmodifiableSet(this.players);
    }

    @Override
    public int count() {
        return this.players.size();
    }

    @Override
    public boolean isTeamMate(@NotNull GamePlayer other) {
        return this.team != null && this.collect().contains(other);
    }

    @Override
    public @NotNull AddResult add(@NotNull GamePlayer player) {
        if (this.team.isFull()) {
            return new TeamFullResult(this.team);
        }

        if (!this.players.add(player)) {
            return new SuccessfulAddResult(this.team);
        }

        return new AlreadyInTeamResult(this.team);
    }

    @Override
    public void remove(@NotNull GamePlayer player) {
        this.players.remove(player);
    }

}
