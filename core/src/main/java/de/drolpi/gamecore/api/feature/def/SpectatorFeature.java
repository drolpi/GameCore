package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SpectatorFeature extends AbstractFeature {

    private static final String TEAM_NAME = "Ghost";
    private final GamePlugin plugin;

    private Team ghost;

    @Inject
    public SpectatorFeature(GamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        Scoreboard scoreboard = this.plugin.getServer().getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(TEAM_NAME);

        if (team == null) {
            this.ghost = scoreboard.registerNewTeam(TEAM_NAME);
        } else {
            this.ghost = team;
        }

        this.ghost.setCanSeeFriendlyInvisibles(true);
    }

    @Override
    public void disable() {
        this.ghost.unregister();
        this.ghost = null;
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        Player player = event.gamePlayer().player();

        player.setGameMode(GameMode.ADVENTURE);
        player.setCollidable(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15, true, false));
        this.ghost.addPlayer(player);

        player.setAllowFlight(true);
        player.setFlying(true);
    }
}
