package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.phase.Phase;
import org.bukkit.GameRule;
import org.bukkit.World;

public class NoTimeChangeFeature extends AbstractFeature {

    private final MapFeature mapFeature;

    @Inject
    public NoTimeChangeFeature(Phase phase) {
        this.mapFeature = phase.feature(MapFeature.class);
    }

    @Expose
    private long time = 6000;

    @Override
    public void enable() {
        final World world = this.mapFeature.world();
        world.setTime(this.time);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    @Override
    public void disable() {

    }
}
