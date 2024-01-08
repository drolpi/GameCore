package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.Title;

public class TitleCounterFeature extends AbstractCounterHandlerFeature {

    private final Game game;
    private final Phase phase;

    @Expose
    private int[] ticks = new int[]{60, 50, 40, 30, 20, 15, 10, 5, 4, 3, 2, 1};

    @Inject
    public TitleCounterFeature(Phase phase, Game game) {
        super(phase);
        this.game = game;
        this.phase = phase;
    }

    @Override
    protected void start(Counter counter) {

    }

    @Override
    protected void tick(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            for (int tick : this.ticks) {
                if (tick != counter.currentCount()) {
                    continue;
                }

                allPlayer.showTitle(
                    Title.title(Component.translatable(this.phase.key() + ".counter_title"), Component.translatable(this.phase.key() + ".counter_subtitle")),
                    Placeholder.component("count", Component.text(counter.currentCount()))
                );
            }
        }
    }

    @Override
    protected void cancel(Counter counter) {

    }

    @Override
    protected void finish(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.showTitle(
                Title.title(Component.translatable(this.phase.key() + ".counter_title_finish"), Component.translatable(this.phase.key() + ".counter_subtitle_finish")),
                Placeholder.component("count", Component.text(counter.currentCount()))
            );
        }
    }
}
