package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;

public class TitleCounterFeature extends AbstractKeyCounterHandlerFeature {

    private final Game game;

    @Inject
    public TitleCounterFeature(Game game, Phase phase) {
        super(phase);
        this.game = game;
    }

    @Override
    protected void perform(Counter counter, String preKey) {
        final Title title = Title.title(
            Component.translatable(preKey + "_title"),
            Component.translatable(preKey + "_subtitle")
        );
        final TagResolver.Single placeholder = Placeholder.component("count", Component.text(counter.currentCount()));
        final TagResolver secondsFormatter = Formatter.choice("unit_seconds", counter.currentCount());

        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.showTitle(title, placeholder, secondsFormatter);
        }
    }
}
