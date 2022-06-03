package net.bote.gamecore.components.team.result;

import net.bote.gamecore.api.game.GameInstance;
import net.bote.gamecore.components.team.TeamInstance;
import org.jetbrains.annotations.NotNull;

public interface AddResult {

    @NotNull TeamInstance team();

    boolean isSuccess();

}
