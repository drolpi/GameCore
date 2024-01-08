package de.drolpi.gamecore.api.feature.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.player.GamePlayerHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessageFeature extends AbstractFeature {

    private final Game game;
    private final Phase phase;
    private final TeamFeature teamFeature;
    private final GamePlayerHandler gamePlayerHandler;

    @Inject
    public DeathMessageFeature(Game game, Phase phase, GamePlayerHandler gamePlayerHandler) {
        this.game = game;
        this.phase = phase;
        this.teamFeature = phase.feature(TeamFeature.class);
        this.gamePlayerHandler = gamePlayerHandler;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        event.deathMessage(null);
        final GamePlayer player = this.gamePlayerHandler.player(event.getPlayer().getUniqueId());

        if (!this.teamFeature.isInTeam(player.uniqueId())) {
            return;
        }

        final TagResolver.Single playerName = Placeholder.component("playername", Component.text(player.player().getName()));
        final Player killer = player.player().getKiller();

        if (killer == null) {
            player.sendMessage(Component.translatable(this.phase.key() + "death_private"), playerName);

            final Component component = Component.translatable(this.phase.key() + "death_brodcast");
            for (GamePlayer allPlayer : this.game.allPlayers()) {
                allPlayer.sendMessage(component, playerName);
            }
            return;
        }

        final TagResolver.Single killerName = Placeholder.component("killername", Component.text(killer.getName()));
        player.sendMessage(Component.translatable(this.phase.key() + "death_killed_private"), playerName, killerName);

        final Component component = Component.translatable(this.phase.key() + "death_killed_brodcast");
        for (GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.sendMessage(component, playerName, killerName);
        }
    }

    //TODO: PlayerTeamRemoveEvent
    //players_remaining
}
