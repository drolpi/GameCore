package de.drolpi.gamecore.api.player;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
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
    public void sendMessage(Component component, TagResolver... resolvers) {
        throw new RuntimeException();
    }

    @Override
    public void showTitle(Title title, TagResolver... resolvers) {
        throw new RuntimeException();
    }

    @Override
    public void showBossBar(String bossBarId, BossBar headBossBar, TagResolver... resolvers) {
        throw new RuntimeException();
    }

    @Override
    public void hideBossBar(String bossBarId, BossBar headBossBar) {
        throw new RuntimeException();
    }

    @Override
    public void playSound(Sound sound) {
        throw new RuntimeException();
    }
}
