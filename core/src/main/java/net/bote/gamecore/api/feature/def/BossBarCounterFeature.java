package net.bote.gamecore.api.feature.def;

import com.google.inject.Inject;
import net.bote.gamecore.api.counter.Counter;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.phase.Phase;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarCounterFeature extends AbstractProgressCounterFeature {

    private final BossBarFeature bossBarFeature;

    private BossBar bossBar;

    @Inject
    public BossBarCounterFeature(Game game, Phase phase) {
        super(game, phase);
        this.bossBarFeature = phase.feature(BossBarFeature.class);
    }

    @Override
    public void enable() {
        super.enable();
        this.bossBar = this.bossBarFeature.bossBar();
    }

    @Override
    public void disable() {
        super.disable();
        this.bossBar = null;
    }

    @Override
    protected void start(Counter counter) {
        this.bossBar.setVisible(true);
    }

    @Override
    protected void cancel(Counter counter) {
        super.cancel(counter);
        this.bossBar.setTitle(this.bossBarFeature.message());
        if (!this.bossBarFeature.message().isBlank()) {
            return;
        }
        this.bossBar.setVisible(false);
    }

    @Override
    protected void set(Player player, int count, float progress) {
        //TODO: Lang
        this.bossBar.setTitle("" + count);
        this.bossBar.setProgress(progress);
    }
}
