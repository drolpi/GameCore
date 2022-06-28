package net.bote.skywars;

import net.bote.gamecore.api.phase.AbstractPhase;

public final class InGamePhase extends AbstractPhase {

    public InGamePhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);
    }
}
