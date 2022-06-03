package net.bote.gamecore.api.phase.def;

import net.bote.gamecore.api.phase.AbstractTimedPhase;

public class LobbyPhase extends AbstractTimedPhase {

    public LobbyPhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(true);
        this.setAllowSpectate(false);
    }
}
