package net.bote.gamecore.api.game;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.Creatable;
import net.bote.gamecore.api.feature.Feature;
import net.bote.gamecore.api.phase.Phase;
import net.bote.gamecore.api.team.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame implements Game, Creatable {

    private final GamePlugin gamePlugin;

    @Expose
    private final String type;
    @Expose
    private int teamSize;
    @Expose
    private final List<Team> teams;
    @Expose
    private final List<Phase> phases;

    public AbstractGame(@NotNull GamePlugin gamePlugin) {
        this.gamePlugin = gamePlugin;
        this.type = this.getClass().getName().replace("GameTypeAdapter.DEFAULT_PATH" + ".", "");
        this.teams = new ArrayList<>();
        this.phases = new ArrayList<>();
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void addTeam(Team team) {

    }

    public <T extends Phase> T addPhase(Class<? extends T> type) {
        return null;
    }

    @Override
    public @NotNull String type() {
        return this.type;
    }

    @Override
    public @NotNull List<Phase> phases() {
        return this.phases;
    }
}
