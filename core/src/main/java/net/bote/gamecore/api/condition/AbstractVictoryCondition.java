package net.bote.gamecore.api.condition;

import net.bote.gamecore.api.AbstractIdentifiable;
import net.bote.gamecore.api.Creatable;
import net.bote.gamecore.api.player.GamePlayer;
import net.bote.gamecore.components.team.TeamInstance;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

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
