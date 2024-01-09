package de.drolpi.gamecore.components.visibility;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.drolpi.gamecore.GamePlugin;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class VisibilityRestrictionHandler {

    private final GamePlugin plugin;
    private final Set<VisibilityRestriction> visibilityRestrictions = new HashSet<>();

    @Inject
    public VisibilityRestrictionHandler(GamePlugin plugin) {
        this.plugin = plugin;
    }

    public void register(VisibilityRestriction visibilityRestriction) {
        this.visibilityRestrictions.add(visibilityRestriction);
        this.updateAll();
    }

    public void unregister(VisibilityRestriction visibilityRestriction) {
        this.visibilityRestrictions.remove(visibilityRestriction);
        this.updateAll();
    }

    public void updateAll(Player player, Player player2) {
        for (VisibilityRestriction restriction : this.visibilityRestrictions) {
            restriction.handleUpdate(player, player2);
        }
    }

    public void updateAll(Player player, Collection<? extends Player> players) {
        for (Player player2 : players) {
            this.updateAll(player, player2);
        }
    }

    public void updateAll(Collection<? extends Player> players, Player player2) {
        for (Player player : players) {
            this.updateAll(player, player2);
        }
    }

    public void updateAll(Collection<? extends Player> players) {
        for (Player player : players) {
            this.updateAll(player, players);
        }
    }

    public void updateAll() {
        this.updateAll(this.plugin.getServer().getOnlinePlayers());
    }
}
