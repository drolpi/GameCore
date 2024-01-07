package de.drolpi.gamecore.components.team.result;

import de.drolpi.gamecore.components.team.TeamInstance;
import org.jetbrains.annotations.NotNull;

public class TeamFullResult extends AbstractAddResult {

    public TeamFullResult(@NotNull TeamInstance team) {
        super(team);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
