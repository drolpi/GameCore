package net.bote.skywars;

import net.bote.gamecore.api.phase.AbstractTimedPhase;
import net.bote.gamecore.api.phase.PhaseInfo;

@PhaseInfo(name = "ProtectionPhase", version = "3.0.0-SNAPSHOT", authors = "dasdrolpi")
public final class ProtectionPhase extends AbstractTimedPhase {

    public ProtectionPhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);
    }
}
