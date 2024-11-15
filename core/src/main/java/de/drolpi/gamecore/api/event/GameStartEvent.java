package de.drolpi.gamecore.api.event;

import de.drolpi.gamecore.api.game.Game;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameStartEvent extends GameEvent {

    private static final HandlerList handlers = new HandlerList();

    public GameStartEvent(Game game) {
        super(game);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
