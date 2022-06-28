package net.bote.gamecore.components.team.result;

import net.bote.gamecore.components.team.TeamInstance;
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
