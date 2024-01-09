package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

public class BossBarCounterFeature extends AbstractCounterProgressFeature {

    private final BossBarFeature bossBarFeature;
    private final BossBar bossBar;

    @Expose
    private BossBar.Color color = BossBar.Color.RED;
    @Expose
    private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;

    @Inject
    public BossBarCounterFeature(Game game, Phase phase) {
        super(game, phase);
        this.bossBarFeature = phase.feature(BossBarFeature.class);
        this.bossBar = BossBar.bossBar(Component.translatable(phase.key() + "counterbossbar_name"), 0.0F, color, overlay);
        this.bossBarFeature.addBossBar(this.bossBar);
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    protected void set(Player player, long count, float progress) {
        this.bossBarFeature.setProgress(this.bossBar, progress);
        this.bossBarFeature.setResolvers(this.bossBar, Placeholder.component("count", Component.text(count)));
    }
}
