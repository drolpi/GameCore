package de.drolpi.gamecore.components.team.result;

import de.drolpi.gamecore.components.team.TeamInstance;
import org.jetbrains.annotations.NotNull;

public class AlreadyInTeamResult extends AbstractAddResult {

    public AlreadyInTeamResult(@NotNull TeamInstance team) {
        super(team);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
