package de.drolpi.gamecore.api.event;

import de.drolpi.gamecore.api.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class GameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Game game;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public GameEvent(Game game) {
        this.game = game;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public Game game() {
        return this.game;
    }
}
