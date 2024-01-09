package de.drolpi.gamecore.components.visibility;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.BiPredicate;

public final class DefaultVisibilityRestriction extends VisibilityRestriction {

    public DefaultVisibilityRestriction(BiPredicate<Player, Player> shouldHide) {
        super(shouldHide);
    }

    @Override
    public void handleUpdate(Player viewer, Player toHide) {
        this.setVisible(viewer, toHide, shouldHide.test(toHide, viewer));
    }

    @Override
    public void handleUpdate(Player viewer, Collection<? extends Player> toHides) {
        for (Player toHide : toHides) {
            this.handleUpdate(viewer, toHide);
        }
    }

    @Override
    public void handleUpdate(Collection<? extends Player> viewers, Player toHide) {
        for (Player viewer : viewers) {
            this.handleUpdate(viewer, toHide);
        }
    }

}
