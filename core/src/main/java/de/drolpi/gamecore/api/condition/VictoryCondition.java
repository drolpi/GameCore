package de.drolpi.gamecore.api.condition;

import de.drolpi.gamecore.components.team.TeamInstance;

public interface VictoryCondition {

    boolean completed();

    TeamInstance winner();

}
