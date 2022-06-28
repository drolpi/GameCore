package net.bote.skywars;

import net.bote.gamecore.api.phase.AbstractTimedPhase;

public final class ProtectionPhase extends AbstractTimedPhase {

    public ProtectionPhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);
    }
}
