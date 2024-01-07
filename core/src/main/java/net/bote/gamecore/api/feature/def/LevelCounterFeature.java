package net.bote.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.counter.Counter;
import net.bote.gamecore.api.counter.HandlerType;
import net.bote.gamecore.api.event.GameJoinEvent;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.phase.Phase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.function.Consumer;

public class LevelCounterFeature extends AbstractProgressCounterFeature {

    @Expose
    private boolean levelProgress = true;

    @Inject
    public LevelCounterFeature(Game game, Phase phase) {
        super(game, phase);
    }

    @Override
    protected void set(Player player, int count, float progress) {
        player.setLevel(count);
        if (!this.levelProgress)
            return;

        player.setExp(progress);
    }
}
