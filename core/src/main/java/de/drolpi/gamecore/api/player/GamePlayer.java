package de.drolpi.gamecore.api.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GamePlayer {

    UUID uniqueId();

    Player player();

    void sendMessage(Component component, TagResolver... resolvers);
}
