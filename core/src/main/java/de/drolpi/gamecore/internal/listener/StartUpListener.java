package de.drolpi.gamecore.internal.listener;

import com.google.inject.Inject;
import de.drolpi.gamecore.GameCorePlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public final class StartUpListener implements Listener {

    //TODO: Disallow while server is starting
    private final GameCorePlugin gameCorePlugin;

    @Inject
    public StartUpListener(GameCorePlugin gameCorePlugin) {
        this.gameCorePlugin = gameCorePlugin;
    }

    @EventHandler
    public void handle(AsyncPlayerPreLoginEvent event) {
        if (this.gameCorePlugin.started()) {
            return;
        }

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Still starting"));
    }
}
