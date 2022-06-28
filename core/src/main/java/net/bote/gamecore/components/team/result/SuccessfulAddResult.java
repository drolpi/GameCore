package net.bote.gamecore.components.team.result;

import net.bote.gamecore.components.team.TeamInstance;
import org.jetbrains.annotations.NotNull;

public class SuccessfulAddResult extends AbstractAddResult {

    public SuccessfulAddResult(@NotNull TeamInstance team) {
        super(team);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
