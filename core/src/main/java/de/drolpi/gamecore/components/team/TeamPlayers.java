package de.drolpi.gamecore.components.team;

import de.drolpi.gamecore.components.team.result.AddResult;
import de.drolpi.gamecore.api.player.GamePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface TeamPlayers {

    @NotNull TeamInstance team();

    @NotNull Set<GamePlayer> collect();

    int count();

    boolean isTeamMate(@NotNull GamePlayer other);

    @NotNull AddResult add(@NotNull GamePlayer player);

    void remove(@NotNull GamePlayer player);

}
