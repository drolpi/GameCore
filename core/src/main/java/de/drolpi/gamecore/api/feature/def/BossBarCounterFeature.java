package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

public class BossBarCounterFeature extends AbstractCounterProgressFeature {

    private final BossBarFeature bossBarFeature;

    @Expose
    private final String replaceBossBarId = "counter";
    private final Component component;
    @Expose
    private BossBar.Color color = BossBar.Color.RED;
    @Expose
    private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;

    @Inject
    public BossBarCounterFeature(Game game, Phase phase) {
        super(game, phase);
        this.bossBarFeature = phase.feature(BossBarFeature.class);
        this.component = Component.translatable(phase.key() + "counter_bossbar");
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    protected void start(Counter counter) {
        super.start(counter);
        if (!this.bossBarFeature.isRegistered(replaceBossBarId)) {
            this.bossBarFeature.register(this.replaceBossBarId, this.color, this.overlay);
        }
        this.bossBarFeature.setColor(this.replaceBossBarId, this.color);
        this.bossBarFeature.setOverlay(this.replaceBossBarId, this.overlay);
    }

    @Override
    protected void cancel(Counter counter) {
        this.resetName();
    }

    @Override
    protected void finish(Counter counter) {
        this.resetName();
    }

    private void resetName() {
        this.bossBarFeature.resetName(this.replaceBossBarId);
    }

    @Override
    protected void tick(Counter counter) {
        this.set(null, counter.currentCount(), super.progress(counter));
    }

    @Override
    protected void set(Player player, long count, float progress) {
        if (!this.bossBarFeature.isRegistered(replaceBossBarId)) {
            this.bossBarFeature.register(this.replaceBossBarId, this.color, this.overlay);
            this.bossBarFeature.setColor(this.replaceBossBarId, this.color);
            this.bossBarFeature.setOverlay(this.replaceBossBarId, this.overlay);
        }
        this.bossBarFeature.setName(this.replaceBossBarId, component);
        this.bossBarFeature.setResolvers(this.replaceBossBarId, Placeholder.component("count", Component.text(count)));
        this.bossBarFeature.setProgress(this.replaceBossBarId, progress);
    }
}
