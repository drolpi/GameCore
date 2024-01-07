package net.bote.gamecore.internal.listener;

import com.google.inject.Inject;
import net.bote.gamecore.api.game.GameController;
import net.bote.gamecore.api.player.GamePlayerHandlerImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class GamePlayerListener implements Listener {

    private final GameController gameController;
    private final GamePlayerHandlerImpl gamePlayerHandler;

    @Inject
    public GamePlayerListener(GameController gameController, GamePlayerHandlerImpl gamePlayerHandler) {
        this.gameController = gameController;
        this.gamePlayerHandler = gamePlayerHandler;
    }

    @EventHandler
    public void handle(AsyncPlayerPreLoginEvent event) {
        if(!this.gamePlayerHandler.login(event.getUniqueId())) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.empty());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void handle(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.translatable("player.join").arguments(Component.text(event.getPlayer().getName())));

        if (!this.gamePlayerHandler.hasLoggedIn(event.getPlayer().getUniqueId())) {
            boolean login = this.gamePlayerHandler.login(event.getPlayer().getUniqueId());
            if (!login || !this.gamePlayerHandler.hasLoggedIn(event.getPlayer().getUniqueId())) {
                // something went horribly wrong
                // we don't have a locale here since the data was not loaded :/
                event.getPlayer().kick(Component.empty());
                return;
            }
        }

        this.gamePlayerHandler.join(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        this.gamePlayerHandler.quit(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void suppressJoinMessages(PlayerJoinEvent event) {
        event.joinMessage(null);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void suppressKickMessages(PlayerKickEvent event) {
        event.leaveMessage(null);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void suppressQuitMessages(PlayerQuitEvent event) {
        event.quitMessage(null);
    }
}
