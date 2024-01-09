package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SpectatorFeature extends AbstractFeature {

    //TODO: remove player from game and add as spectator

    private static final String TEAM_NAME = "Spectator";
    private final GamePlugin plugin;
    private final Game game;
    private final Phase phase;

    private Team spectator;

    @Inject
    public SpectatorFeature(GamePlugin plugin, Game game, Phase phase) {
        this.plugin = plugin;
        this.game = game;
        this.phase = phase;
    }

    @Override
    public void enable() {
        Scoreboard scoreboard = this.plugin.getServer().getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(TEAM_NAME);

        if (team == null) {
            this.spectator = scoreboard.registerNewTeam(TEAM_NAME);
        } else {
            this.spectator = team;
        }

        this.spectator.setCanSeeFriendlyInvisibles(true);
        this.spectator.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        // set all players who have been spectators since a previous phase
        for (final GamePlayer spectator : this.game.spectators()) {
            this.setSpectator(spectator);
        }
    }

    @Override
    public void disable() {
        this.spectator.unregister();
        this.spectator = null;
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        final GamePlayer gamePlayer = event.gamePlayer();
        this.setSpectator(gamePlayer);
        gamePlayer.sendMessage(Component.translatable("set_spectator"));
    }

    private void setSpectator(GamePlayer gamePlayer) {
        Player player = gamePlayer.player();
        if (!this.game.isSpectating(player.getUniqueId())) {
            return;
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.setCollidable(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15, true, false));
        this.spectator.addPlayer(player);

        player.setAllowFlight(true);
        player.setFlying(true);
    }
}
