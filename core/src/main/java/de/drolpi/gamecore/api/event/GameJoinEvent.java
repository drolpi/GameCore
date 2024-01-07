package de.drolpi.gamecore.api.event;

import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameJoinEvent extends GameEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final GamePlayer gamePlayer;
    private boolean cancelled;

    public GameJoinEvent(Game game, GamePlayer gamePlayer) {
        super(game);
        this.gamePlayer = gamePlayer;
    }

    public GamePlayer gamePlayer() {
        return this.gamePlayer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
