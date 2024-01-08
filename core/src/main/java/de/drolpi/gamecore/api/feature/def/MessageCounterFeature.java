package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class MessageCounterFeature extends AbstractCounterHandlerFeature {

    private final Game game;
    private final Phase phase;

    @Expose
    private int[] ticks = new int[]{60, 50, 40, 30, 20, 15, 10, 5, 4, 3, 2, 1};

    @Inject
    public MessageCounterFeature(Game game, Phase phase) {
        super(phase);
        this.game = game;
        this.phase = phase;
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
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

                allPlayer.sendMessage(
                    Component.translatable(this.phase.key() + ".counter_tick"),
                    Placeholder.component("count", Component.text(counter.currentCount())),
                    Formatter.choice("unit_seconds", counter.currentCount())
                );
            }
        }
    }

    @Override
    protected void cancel(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.sendMessage(Component.translatable(this.phase.key() + ".counter_cancel"));
        }
    }

    @Override
    protected void finish(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.sendMessage(Component.translatable(this.phase.key() + ".counter_finish"));
        }
    }
}
