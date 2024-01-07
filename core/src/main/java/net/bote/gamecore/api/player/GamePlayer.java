package net.bote.gamecore.api.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GamePlayer {

    UUID uniqueId();

    Player player();

    void sendMessage(Component component);
}
