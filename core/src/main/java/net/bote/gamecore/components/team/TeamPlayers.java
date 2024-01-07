package net.bote.gamecore.components.team;

import net.bote.gamecore.api.player.GamePlayer;
import net.bote.gamecore.components.team.result.AddResult;
import org.bukkit.entity.Player;
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
