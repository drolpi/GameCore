package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public class BossBarCounterFeature extends AbstractCounterProgressFeature {

    private final BossBarFeature bossBarFeature;

    @Inject
    public BossBarCounterFeature(Game game, Phase phase) {
        super(game, phase);
        this.bossBarFeature = phase.feature(BossBarFeature.class);
    }

    @Override
    protected void set(Player player, long count, float progress) {
        this.bossBarFeature.setProgress(progress);
        this.bossBarFeature.setResolvers(new TagResolver[]{
            Placeholder.component("count", Component.text(count))
        });
    }
}
