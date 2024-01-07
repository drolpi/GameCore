package de.drolpi.gamecore.api.condition;

import de.drolpi.gamecore.api.Creatable;
import de.drolpi.gamecore.components.team.TeamInstance;
import de.drolpi.gamecore.api.AbstractIdentifiable;
import org.bukkit.event.Listener;

public abstract class AbstractVictoryCondition extends AbstractIdentifiable implements VictoryCondition, Creatable, Listener {

    protected TeamInstance winner;

    public AbstractVictoryCondition() {

    }

    public abstract TeamInstance condition();

    @Override
    public boolean completed() {
        this.winner = this.condition();

        return this.winner != null;
    }

    @Override
    public TeamInstance winner() {
        return this.winner;
    }
}
