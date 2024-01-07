package net.bote.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.components.team.TeamInstanceImpl;
import net.bote.gamecore.components.team.config.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamSelectFeature extends TeamFeature {

    @Expose
    private int teamSize;
    @Expose
    private final List<Team> teams = new ArrayList<>();

    @Inject
    public TeamSelectFeature(Game game) {
        super(game);
    }

    @Override
    public void enable() {
        this.teamInstances = new HashMap<>();
        for (Team team : this.teams) {
            this.teamInstances.put(team.name(), new TeamInstanceImpl(team, this.teamSize));
        }
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }
}
