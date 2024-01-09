package de.drolpi.gamecore.components.visibility;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.BiPredicate;

public abstract class VisibilityRestriction {

    protected final BiPredicate<Player, Player> shouldHide; //<T, U> -> <toHide, viewer> : Should T be hidden from U?

    public VisibilityRestriction(BiPredicate<Player, Player> shouldHide) {
        this.shouldHide = shouldHide;
    }

    public abstract void handleUpdate(Player player, Player player2);

    public abstract void handleUpdate(Player player, Collection<? extends Player> players);

    public abstract void handleUpdate(Collection<? extends Player> players, Player player2);

    protected void setVisible(Player player, Player player2, boolean hide) {
        if (hide) {
            player.hidePlayer(player2);
        } else {
            player.showPlayer(player2);
        }
    }
}
