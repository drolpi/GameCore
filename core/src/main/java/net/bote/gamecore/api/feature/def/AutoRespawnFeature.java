package net.bote.gamecore.api.feature.def;

import com.google.inject.Inject;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.phase.Phase;
import org.bukkit.GameRule;

public class AutoRespawnFeature extends AbstractFeature {

    private final MapFeature mapFeature;

    @Inject
    public AutoRespawnFeature(Phase phase) {
        this.mapFeature = phase.feature(MapFeature.class);
    }

    @Override
    public void enable() {
        this.mapFeature.world().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
    }

    @Override
    public void disable() {
        this.mapFeature.world().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, false);
    }
}
