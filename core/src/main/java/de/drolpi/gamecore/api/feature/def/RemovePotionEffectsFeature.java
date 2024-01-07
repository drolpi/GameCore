package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
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
