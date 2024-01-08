package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.sound.Sound;

public class SoundCounterFeature extends AbstractCounterHandlerConfigFeature {

    private final Game game;
    private final Sound play;

    @Expose
    private org.bukkit.Sound sound = org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING;
    @Expose
    private float volume = 1;
    @Expose
    private float pitch = 1;

    @Inject
    public SoundCounterFeature(Game game, Phase phase) {
        super(phase);
        this.game = game;
        this.play = Sound.sound(this.sound, Sound.Source.AMBIENT, this.volume, this.pitch);
    }

    @Override
    protected void start(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.playSound(this.play);
        }
    }

    @Override
    protected void tick(Counter counter) {
        if (!this.shouldTick(counter.currentCount())) {
            return;
        }

        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.playSound(this.play);
        }
    }

    @Override
    protected void cancel(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.playSound(this.play);
        }
    }

    @Override
    protected void finish(Counter counter) {
        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.playSound(this.play);
        }
    }
}
