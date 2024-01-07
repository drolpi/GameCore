package net.bote.gamecore.api.player;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.Translator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

final class GamePlayerImpl extends PreGamePlayerImpl implements GamePlayer {

    private final Player player;

    GamePlayerImpl(Player player) {
        super(player.getUniqueId());
        this.player = player;
    }

    GamePlayerImpl(PreGamePlayerImpl gamePlayer, Player player) {
        super(gamePlayer.uniqueId);
        this.player = player;
    }

    @Override
    public Player player() {
        return this.player;
    }

    @Override
    public void sendMessage(Component component) {
        TranslatableComponentRenderer renderer = TranslatableComponentRenderer.usingTranslationSource(new Translator() {
            @Override
            public @NotNull Key name() {
                return Key.key("translator");
            }

            @Override
            public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
                return null;
            }

            @Override
            public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
                String value = component.key();
                return MiniMessage.miniMessage().deserialize(value);
            }
        });
    }
}
