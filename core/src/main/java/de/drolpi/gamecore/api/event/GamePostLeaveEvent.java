package de.drolpi.gamecore.api.event;

import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GamePostLeaveEvent extends GameEvent {

    private static final HandlerList handlers = new HandlerList();
    private final GamePlayer gamePlayer;

    public GamePostLeaveEvent(Game game, GamePlayer gamePlayer) {
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
}
