package de.drolpi.gamecore.api.player;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GamePlayer {

    UUID uniqueId();

    Player player();

    void sendMessage(Component component, TagResolver... resolvers);

    void showTitle(Title title, TagResolver... resolvers);

    void showBossBar(String bossBarId, BossBar headBossBar, TagResolver... resolvers);

    void hideBossBar(String bossBarId, BossBar headBossBar);

    void playSound(Sound sound);
}
