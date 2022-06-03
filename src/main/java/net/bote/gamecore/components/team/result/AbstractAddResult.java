package net.bote.gamecore.components.team.result;

import net.bote.gamecore.components.team.TeamInstance;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAddResult implements AddResult {

    private final TeamInstance team;

    protected AbstractAddResult(TeamInstance team) {
        this.team = team;
    }

    public @NotNull TeamInstance team() {
        return team;
    }
}
