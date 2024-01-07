package net.bote.gamecore.api.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

class PreGamePlayerImpl implements GamePlayer {

    protected final UUID uniqueId;

    PreGamePlayerImpl(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public UUID uniqueId() {
        return this.uniqueId;
    }

    @Override
    public Player player() {
        throw new RuntimeException();
    }

    @Override
    public void sendMessage(Component component) {
        throw new RuntimeException();
    }
}
