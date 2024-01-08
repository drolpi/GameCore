package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.phase.Phase;

public class StartingFeature extends AbstractFeature {

    private final CounterFeature counterFeature;
    private final SpawnFeature spawnFeature;

    @Inject
    public StartingFeature(Phase phase) {
        this.counterFeature = phase.feature(CounterFeature.class);
        this.spawnFeature = phase.feature(SpawnFeature.class);
    }

    @Override
    public void enable() {
        this.spawnFeature.registerHandler(() -> {
            this.counterFeature.counter().start();
        });
    }

    @Override
    public void disable() {

    }
}
