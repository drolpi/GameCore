package net.bote.gamecore.api.phase;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.condition.VictoryCondition;
import net.bote.gamecore.api.Creatable;
import net.bote.gamecore.api.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPhase implements Phase, Creatable {

    @Expose
    private final String type;

    @Expose
    private final Set<Feature> features;

    @Expose
    private final Set<VictoryCondition> victoryConditions;

    @Expose
    private boolean allowJoin;

    @Expose
    private boolean allowSpectate;

    public AbstractPhase() {
        this.type = this.getClass().getName().replace("PhaseTypeAdapter.DEFAULT_PATH" + ".", "");
        this.features = new HashSet<>();
        this.victoryConditions = new HashSet<>();
    }

    public <T extends Feature> T addFeature(Class<? extends T> type) {
        return null;
    }

    public <T extends VictoryCondition> T addVictoryCondition(Class<? extends T> type) {
        return null;
    }

    public void setAllowJoin(boolean allowJoin) {
        this.allowJoin = allowJoin;
    }

    public void setAllowSpectate(boolean allowSpectate) {
        this.allowSpectate = allowSpectate;
    }


    @Override
    public @NotNull String type() {
        return this.type;
    }

    @Override
    public @NotNull Set<Feature> features() {
        return this.features;
    }

    @Override
    public @NotNull Set<VictoryCondition> victoryConditions() {
        return this.victoryConditions;
    }
}
