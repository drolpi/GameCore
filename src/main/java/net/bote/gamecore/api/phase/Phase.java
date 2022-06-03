package net.bote.gamecore.api.phase;

import net.bote.gamecore.api.condition.VictoryCondition;
import net.bote.gamecore.api.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Phase {

    @NotNull String type();

    @NotNull Set<Feature> features();

    @NotNull Set<VictoryCondition> victoryConditions();

}
