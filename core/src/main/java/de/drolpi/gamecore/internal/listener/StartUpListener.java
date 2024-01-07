package de.drolpi.gamecore.internal.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public final class StartUpListener implements Listener {

    //TODO: Disallow while server is starting

    @EventHandler
    public void handle(AsyncPlayerPreLoginEvent event) {

    }
}
