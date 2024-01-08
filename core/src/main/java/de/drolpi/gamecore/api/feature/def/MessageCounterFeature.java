package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MessageCounterFeature extends AbstractKeyCounterHandlerFeature {

    private final Game game;

    @Inject
    public MessageCounterFeature(Game game, Phase phase) {
        super(phase);
        this.game = game;
    }

    @Override
    protected void perform(Counter counter, String preKey) {
        final Component component = Component.translatable(preKey + "_message");
        final TagResolver.Single placerHolder = Placeholder.component("count", Component.text(counter.currentCount()));

        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.sendMessage(component, placerHolder);
        }
    }
}
