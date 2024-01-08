package de.drolpi.gamecore.api.phase;

import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Phase {

    String key();

    <T extends AbstractFeature> T feature(Class<T> type);

    @NotNull Set<Feature> features();

}
