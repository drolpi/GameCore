package net.bote.skywars;

import net.bote.gamecore.api.phase.AbstractPhase;
import net.bote.gamecore.api.phase.PhaseInfo;

@PhaseInfo(name = "InGamePhase", version = "3.0.0-SNAPSHOT", authors = "dasdrolpi")
public final class InGamePhase extends AbstractPhase {

    public InGamePhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);
    }
}
