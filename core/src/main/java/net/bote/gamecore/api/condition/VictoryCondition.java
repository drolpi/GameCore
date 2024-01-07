package net.bote.gamecore.api.condition;

import net.bote.gamecore.components.team.TeamInstance;

public interface VictoryCondition {

    boolean completed();

    TeamInstance winner();

}
