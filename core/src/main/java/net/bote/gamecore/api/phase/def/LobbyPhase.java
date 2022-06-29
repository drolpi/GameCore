package net.bote.gamecore.api.phase.def;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.phase.AbstractTimedPhase;
import net.bote.gamecore.api.phase.PhaseInfo;

@PhaseInfo(name = "LobbyPhase", version = "1.0", authors = "dasdrolpi")
public class LobbyPhase extends AbstractTimedPhase {

    @Expose
    private int minPlayers;

    public LobbyPhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(true);
        this.setAllowSpectate(false);
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
}
