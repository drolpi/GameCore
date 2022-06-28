package net.bote.gamecore.components.team;

import net.bote.gamecore.components.team.result.AddResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface TeamPlayers {

    @NotNull TeamInstance team();

    @NotNull Set<Player> collect();

    int count();

    boolean isTeamMate(@NotNull Player other);

    @NotNull AddResult add(@NotNull Player player);

    void remove(@NotNull Player player);

}
