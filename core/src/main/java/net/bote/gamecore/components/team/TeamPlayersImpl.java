package net.bote.gamecore.components.team;

import net.bote.gamecore.api.player.GamePlayer;
import net.bote.gamecore.components.team.result.AddResult;
import net.bote.gamecore.components.team.result.AlreadyInTeamResult;
import net.bote.gamecore.components.team.result.SuccessfulAddResult;
import net.bote.gamecore.components.team.result.TeamFullResult;
import org.bukkit.entity.Player;
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
