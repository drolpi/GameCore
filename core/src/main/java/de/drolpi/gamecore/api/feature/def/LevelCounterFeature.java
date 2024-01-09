package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.counter.Counter;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import org.bukkit.entity.Player;

public class LevelCounterFeature extends AbstractCounterProgressFeature {

    @Expose
    private boolean levelProgress = true;

    @Inject
    public LevelCounterFeature(Game game, Phase phase) {
        super(game, phase);
    }

    @Override
    protected void set(Player player, long count, float progress) {
        player.setLevel((int) count);
        if (!this.levelProgress)
            return;

        player.setExp(progress);
    }
}
