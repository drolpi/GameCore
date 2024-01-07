package net.bote.gamecore.api.phase;

import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.feature.Feature;
import net.bote.gamecore.api.game.GameData;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface Phase {

    <T extends AbstractFeature> T feature(Class<T> type);

    @NotNull Set<Feature> features();

}
