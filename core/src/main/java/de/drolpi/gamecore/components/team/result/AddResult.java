package de.drolpi.gamecore.components.team.result;

import de.drolpi.gamecore.components.team.TeamInstance;
import org.jetbrains.annotations.NotNull;

public interface AddResult {

    @NotNull TeamInstance team();

    boolean isSuccess();

}
