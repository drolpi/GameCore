package net.bote.gamecore.api.feature.def;

import com.google.inject.Inject;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.game.Game;
import net.bote.gamecore.api.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class RemovePotionEffectsFeature extends AbstractFeature {

    private final Game game;

    @Inject
    public RemovePotionEffectsFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {
        for (GamePlayer gamePlayer : this.game.players()) {
            Player player = gamePlayer.player();
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    @Override
    public void disable() {

    }
}
