package de.drolpi.gamecore.internal.listener;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.game.AbstractGame;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.player.GamePlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.Set;

public final class GameListener implements Listener {

    private final GameControllerImpl gameController;
    private final GamePlayerHandler gamePlayerHandler;

    @Inject
    public GameListener(GameControllerImpl gameController, GamePlayerHandler gamePlayerHandler) {
        this.gameController = gameController;
        this.gamePlayerHandler = gamePlayerHandler;
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        final Optional<AbstractGame> optional = this.gameController.defaultGame();

        if (optional.isEmpty()) {
            return;
        }

        final AbstractGame game = optional.get();
        final GamePlayer gamePlayer = this.gamePlayerHandler.player(event.getPlayer().getUniqueId());
        game.join(gamePlayer);
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        final GamePlayer player = this.gamePlayerHandler.player(event.getPlayer().getUniqueId());
        final Set<AbstractGame> games = this.gameController.abstractGames(player, true);

        for (AbstractGame game : games) {
            game.leave(player);
        }
    }
}
